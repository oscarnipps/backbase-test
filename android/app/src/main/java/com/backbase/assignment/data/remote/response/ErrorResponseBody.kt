package com.backbase.assignment.data.remote.response


import com.google.gson.annotations.SerializedName

data class ErrorResponseBody(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String,
    @SerializedName("success")
    val success: Boolean
)