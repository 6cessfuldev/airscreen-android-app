package com.yuk.airscreen.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T> (
    @SerializedName("response")
    val response: ResponseData<T>
)

data class ResponseData<T>(
    @SerializedName("header")
    val header: HeaderData,

    @SerializedName("body")
    val body: T
)

data class HeaderData(
    @SerializedName("resultCode")
    val resultCode: String,

    @SerializedName("resultMsg")
    val resultMsg: String
)