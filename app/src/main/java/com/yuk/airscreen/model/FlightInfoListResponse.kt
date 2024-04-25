package com.yuk.airscreen.model

import com.google.gson.annotations.SerializedName

data class FlightInfoListResponse (
    @SerializedName("status")
    val status: Int?,

    @SerializedName("items")
    val items: List<DepartingFlightsInfo>
)
data class DepartingFlightsInfo(
    @SerializedName("airline")
    val airline: String?,

    @SerializedName("flightId")
    val flightId: String?,

    @SerializedName("scheduleDateTime")
    val scheduleDateTime: String?,

    @SerializedName("estimatedDateTime")
    val estimatedDateTime: String?,

    @SerializedName("airport")
    val airport: String?,

    @SerializedName("chkinrange")
    val chkinrange: String?,

    @SerializedName("gatenumber")
    val gatenumber: String?,

    @SerializedName("codeshare")
    val codeshare: String?,

    @SerializedName("masterflightid")
    val masterflightid: String?,

    @SerializedName("remark")
    val remark: String?,

    @SerializedName("airportCode")
    val airportCode: String?,

    @SerializedName("terminalid")
    val terminalid: String?,

    @SerializedName("typeOfFlight")
    val typeOfFlight: String?,

    @SerializedName("fid")
    val fid: String?,

    @SerializedName("fstandposition")
    val fstandposition: String?
)