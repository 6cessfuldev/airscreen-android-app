package com.yuk.airscreen

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuk.airscreen.adapter.TableRowAdapter
import com.yuk.airscreen.model.ApiResponse
import com.yuk.airscreen.model.DepartingFlightsInfo
import com.yuk.airscreen.model.FlightInfoListResponse
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
 * Use the [Search.newInstance] factory method to
 * create an instance of this fragment.
 */
class Search : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var keyword: String? = null
    private lateinit var searchButton : ImageButton
    private lateinit var tableRecyclerView : RecyclerView
    private var flightList = ArrayList<DepartingFlightsInfo>()
    private lateinit var tableRowAdapter: TableRowAdapter

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
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        tableRecyclerView = view.findViewById(R.id.table_recycler_view)
        searchButton = view.findViewById(R.id.search_button)
        searchButton.setOnClickListener {
            val searchInputTextView: EditText = view.findViewById(R.id.search_input_text)
            keyword = searchInputTextView.text.toString()
            search { (response) ->
                val data = response.body.items
                flightList = data as ArrayList<DepartingFlightsInfo>

                if(isAdded){
                    tableRowAdapter = TableRowAdapter(flightList, onTableRowClick)
                    tableRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    tableRecyclerView.adapter = tableRowAdapter
                }
            }
        }
        return view
    }

    private val onTableRowClick: (DepartingFlightsInfo) -> Unit = { departingFlightsInfo ->
        val intent: Intent = Intent(context, FlightInfoActivity::class.java)
        intent.putExtra("flightInfo", departingFlightsInfo)
        startActivity(intent)
    }

    private fun search(onSuccess: (ApiResponse<FlightInfoListResponse>) -> Unit) {
        val currentDateTime = LocalDateTime.now()
        val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val datePart = currentDateTime.format(dateFormatter)

        val apiService = ApiClient.create()
        val call = apiService.fetchFlightInfoData(

            fromTime = "0000", searchDay = datePart.toString(), inqtimechcd = "E", flight_id = keyword
        )
        call.enqueue(object : Callback<ApiResponse<FlightInfoListResponse>> {
            override fun onResponse(
                call: Call<ApiResponse<FlightInfoListResponse>>,
                response: Response<ApiResponse<FlightInfoListResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!)
                } else {
                    Log.e(ContentValues.TAG, "API 호출 실패: " + response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse<FlightInfoListResponse>>, t: Throwable) {
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
         * @return A new instance of fragment Search.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Search().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}