package com.yuk.airscreen

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yuk.airscreen.model.ApiResponse
import com.yuk.airscreen.model.FlightInfoListResponse
import com.yuk.airscreen.model.PassengerNoticeResponse
import com.yuk.airscreen.service.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PassengerNotice.newInstance] factory method to
 * create an instance of this fragment.
 */
class PassengerNotice : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_passenger_notice, container, false)
        dataFetch{(response) ->
            if(isAdded) {
                val strNum: String? = response.body.items.last().t1sumset2
                val modifiedStrNum: Int? = strNum?.toDouble()?.toInt()
                view.findViewById<TextView>(R.id.info_passenger_notice_tv).text = modifiedStrNum.toString()
            }
        }
        return view
    }

    private fun dataFetch(onSuccess: (ApiResponse<PassengerNoticeResponse>) -> Unit) {
        val apiService = ApiClient.create()
        val call = apiService.fetchPassengerNoticeData(
        )
        call.enqueue(object : Callback<ApiResponse<PassengerNoticeResponse>> {
            override fun onResponse(
                call: Call<ApiResponse<PassengerNoticeResponse>>,
                response: Response<ApiResponse<PassengerNoticeResponse>>
            ) {
                Log.d(ContentValues.TAG, "onResponse: hello")
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!)
                } else {
                    Log.e(ContentValues.TAG, "API 호출 실패: " + response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse<PassengerNoticeResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "API 호출 실패: ${t.message}")
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PassengerNotice.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PassengerNotice().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}