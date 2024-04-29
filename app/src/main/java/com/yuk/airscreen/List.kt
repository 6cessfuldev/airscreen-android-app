package com.yuk.airscreen

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.yuk.airscreen.adapter.TableRowAdapter
import com.yuk.airscreen.model.ApiResponse
import com.yuk.airscreen.model.DepartingFlightsInfo
import com.yuk.airscreen.model.FlightInfoListResponse
import com.yuk.airscreen.service.apiclient.ApiClient
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [List.newInstance] factory method to
 * create an instance of this fragment.
 */
class List : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var selectedTerminal: String = "제1터미널"
    private var selectedCounter: String = "A"
    private var lastUpdateDate: String  = ""

    private lateinit var tableRecyclerView : RecyclerView
    private var flightList = ArrayList<DepartingFlightsInfo>()
    private var filteredList = ArrayList<DepartingFlightsInfo>()
    private lateinit var tableRowAdapter: TableRowAdapter
    private lateinit var lastUpdateTextView : TextView

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
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        lastUpdateTextView = view.findViewById(R.id.last_update_tv)
        tableRecyclerView = view.findViewById(R.id.table_recycler_view)

        val spinner: Spinner = view.findViewById(R.id.spinner2)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.terminal,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val spinner2: Spinner = view.findViewById(R.id.spinner3)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.counter,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedTerminal = parent?.getItemAtPosition(position).toString()

                if(::tableRecyclerView.isInitialized && ::tableRowAdapter.isInitialized){
                    filteredList = getFilterData()
                    tableRowAdapter.updateData(filteredList)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCounter = parent?.getItemAtPosition(position).toString()
                if(::tableRecyclerView.isInitialized && ::tableRowAdapter.isInitialized){
                    filteredList = getFilterData()
                    tableRowAdapter.updateData(filteredList)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        dataFetch { (response) ->
            val data = response.body.items
            flightList = data as ArrayList<DepartingFlightsInfo>
            filteredList = getFilterData()

            lastUpdateDate = getCurrentDateTimeAsString()
            lastUpdateTextView.text = lastUpdateDate

            tableRowAdapter = TableRowAdapter(filteredList, onTableRowClick)
            if(isAdded){
                tableRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                tableRecyclerView.adapter = tableRowAdapter
            }
        }

        return view
    }

    private val onTableRowClick: (DepartingFlightsInfo) -> Unit = { departingFlightsInfo ->
        val intent: Intent = Intent(context, FlightInfoActivity::class.java)
        intent.putExtra("flightInfo", departingFlightsInfo)
        startActivity(intent)
    }

    private fun dataFetch(onSuccess: (ApiResponse<FlightInfoListResponse>) -> Unit) {
        val currentDateTime = LocalDateTime.now()
        val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val datePart = currentDateTime.format(dateFormatter)

        val apiService = ApiClient.create()
        val call = apiService.fetchFlightInfoData(
            fromTime = "0000", searchDay = datePart.toString(), inqtimechcd = "E"
        )
        call.enqueue(object : Callback<ApiResponse<FlightInfoListResponse>> {
            override fun onResponse(
                call: Call<ApiResponse<FlightInfoListResponse>>,
                response: Response<ApiResponse<FlightInfoListResponse>>
            ) {
                Log.d(TAG, "onResponse: hello")
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!)
                } else {
                    Log.e(TAG, "API 호출 실패: " + response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse<FlightInfoListResponse>>, t: Throwable) {
                Log.e(TAG, "API 호출 실패: ${t.message}")
            }
        })
    }

    private fun getFilterData() : ArrayList<DepartingFlightsInfo> {
        return flightList.filter { info ->
            if (selectedTerminal == "제1터미널" && info.terminalid != "P01" && info.terminalid != "P02") {
                false
            } else if (selectedTerminal == "제2터미널" && info.terminalid != "P03") {
                false
            } else !(selectedCounter.isNotEmpty() && info.chkinrange?.isNotEmpty() == true
                    && selectedCounter[0] != (info.chkinrange?.get(0) ?: ' '))
        } as ArrayList
    }

    private fun getCurrentDateTimeAsString(): String {
        val currentDateTime = LocalDateTime.now()
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val datePart = currentDateTime.format(dateFormatter)
        val timePart = currentDateTime.format(timeFormatter)
        return "$datePart\n$timePart"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment List.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            List().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}