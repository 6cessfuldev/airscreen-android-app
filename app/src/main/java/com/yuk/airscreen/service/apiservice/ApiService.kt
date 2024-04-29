package com.yuk.airscreen.service.apiservice

import com.yuk.airscreen.model.ApiResponse
import com.yuk.airscreen.model.FlightInfoListResponse
import com.yuk.airscreen.model.PassengerNoticeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/B551177/StatusOfPassengerFlightsDeOdp/getPassengerDeparturesDeOdp")
    fun fetchFlightInfoData(
        @Query("serviceKey") serviceKey: String = "JQ56Q7dvrbHvWwUq9msT/g8r82XXmvqxlA1Gechk8hHuc8rp9MhouuPWtDP5xItTsYMIQtHqgvhf4r0N1cRtrA==",
        @Query("pageNo") pageNo: Int = 1,
        @Query("numOfRows") numOfRows: Int = 4000,
        @Query("type") type: String = "json",
        @Query("from_time") fromTime: String,
        @Query("searchday") searchDay: String,
        @Query("inqtimechcd") inqtimechcd: String,
        @Query("flight_id") flight_id: String? = null
    ): Call<ApiResponse<FlightInfoListResponse>>

    @GET("/B551177/PassengerNoticeKR/getfPassengerNoticeIKR")
    fun fetchPassengerNoticeData(
        @Query("serviceKey") serviceKey: String = "JQ56Q7dvrbHvWwUq9msT/g8r82XXmvqxlA1Gechk8hHuc8rp9MhouuPWtDP5xItTsYMIQtHqgvhf4r0N1cRtrA==",
        @Query("type") type: String = "json",
        @Query("selectdate") selectdate: String = "0"
    ): Call<ApiResponse<PassengerNoticeResponse>>
}