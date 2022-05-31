package com.backbase.assignment.util

import com.backbase.assignment.data.remote.response.ErrorResponseBody
import com.google.gson.GsonBuilder
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

object ErrorMessageUtil {

    fun getErrorMessageFromResponse(error: Throwable): String {
        (error as? HttpException)?.let {
            Timber.d("error code : ${error.code()}")

            val gson = GsonBuilder().create()

            val errorResponse =
                gson.fromJson(
                    error.response()?.errorBody()?.charStream(),
                    ErrorResponseBody::class.java
                )

            Timber.d("error message : ${errorResponse.statusMessage}")

            return errorResponse.statusMessage
        }

        Timber.d("error message : ${error.localizedMessage}")

        return "unknown error occurred , please try again"
    }
}