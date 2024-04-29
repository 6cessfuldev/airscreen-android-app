package com.yuk.airscreen.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class FlightInfoListResponse(
    @SerializedName("numOfRows")
    val numOfRows: Int,

    @SerializedName("pageNo")
    val pageNo: Int,

    @SerializedName("totalCount")
    val totalCount: Int,

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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(airline)
        parcel.writeString(flightId)
        parcel.writeString(scheduleDateTime)
        parcel.writeString(estimatedDateTime)
        parcel.writeString(airport)
        parcel.writeString(chkinrange)
        parcel.writeString(gatenumber)
        parcel.writeString(codeshare)
        parcel.writeString(masterflightid)
        parcel.writeString(remark)
        parcel.writeString(airportCode)
        parcel.writeString(terminalid)
        parcel.writeString(typeOfFlight)
        parcel.writeString(fid)
        parcel.writeString(fstandposition)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DepartingFlightsInfo> {
        override fun createFromParcel(parcel: Parcel): DepartingFlightsInfo {
            return DepartingFlightsInfo(parcel)
        }

        override fun newArray(size: Int): Array<DepartingFlightsInfo?> {
            return arrayOfNulls(size)
        }
    }
}