package com.yuk.airscreen.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PassengerNoticeResponse(
    @SerializedName("status")
    val status: String?,

    @SerializedName("items")
    val items: List<PassengerNoticeInfo>
)

data class PassengerNoticeInfo (
    @SerializedName("adate")
    val adate: String?,

    @SerializedName("atime")
    val atime: String?,

    @SerializedName("t1sum2")
    val t1sum2: String?,

    @SerializedName("t1sum3")
    val t1sum3: String?,

    @SerializedName("t1sum1")
    val t1sum1: String?,

    @SerializedName("t1sum4")
    val t1sum4: String?,

    @SerializedName("t1sumset1")
    val t1sumset1: String?,

    @SerializedName("t1sum5")
    val t1sum5: String?,

    @SerializedName("t1sum6")
    val t1sum6: String?,

    @SerializedName("t1sum7")
    val t1sum7: String?,

    @SerializedName("t1sum8")
    val t1sum8: String?,

    @SerializedName("t1sumset2")
    val t1sumset2: String?,

    @SerializedName("t2sum1")
    val t2sum1: String?,

    @SerializedName("t2sum2")
    val t2sum2: String?,

    @SerializedName("t2sumset1")
    val t2sumset1: String?,

    @SerializedName("t2sum3")
    val t2sum3: String?,

    @SerializedName("t2sum4")
    val t2sum4: String?,

    @SerializedName("t2sumset2")
    val t2sumset2: String?
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
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(adate)
        parcel.writeString(atime)
        parcel.writeString(t1sum2)
        parcel.writeString(t1sum3)
        parcel.writeString(t1sum1)
        parcel.writeString(t1sum4)
        parcel.writeString(t1sumset1)
        parcel.writeString(t1sum5)
        parcel.writeString(t1sum6)
        parcel.writeString(t1sum7)
        parcel.writeString(t1sum8)
        parcel.writeString(t1sumset2)
        parcel.writeString(t2sum1)
        parcel.writeString(t2sum2)
        parcel.writeString(t2sumset1)
        parcel.writeString(t2sum3)
        parcel.writeString(t2sum4)
        parcel.writeString(t2sumset2)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PassengerNoticeInfo> {
        override fun createFromParcel(parcel: Parcel): PassengerNoticeInfo {
            return PassengerNoticeInfo(parcel)
        }

        override fun newArray(size: Int): Array<PassengerNoticeInfo?> {
            return arrayOfNulls(size)
        }
    }
}
