package com.example.mvvmapp.api

import android.accounts.NetworkErrorException
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.net.ssl.HttpsURLConnection

object ResponseHandler {

    fun handleErrorResponse(error: Throwable): String {
        return when (error) {
            is SocketTimeoutException -> "Timeout, Might be Server not responding"
            is NetworkErrorException, is IOException -> "No Internet Connection"
            is HttpException -> {
                when (error.code()) {
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> "Unauthorised User"
                    HttpsURLConnection.HTTP_FORBIDDEN -> "Forbidden"
                    HttpsURLConnection.HTTP_INTERNAL_ERROR -> "Internal Server Error"
                    HttpsURLConnection.HTTP_BAD_REQUEST -> "Bad Request"
                    else -> error.getLocalizedMessage()
                }
            }
            is JsonSyntaxException -> "Something Went Wrong. API is not responding properly!"
            else -> error.message.toString()
        }
    }


}