package com.example.mvvmapp.api

import com.example.mvvmapp.utils.Url
import com.example.mvvmapp.model.BaseModel
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response

object ParserHelper {
    fun baseError(error: ResponseBody?): BaseModel {
        return try {
            Gson().fromJson(error!!.charStream(), BaseModel::class.java)
        } catch (e: Exception) {
            var list= arrayListOf<String>()
            list.add(ResponseHandler.handleErrorResponse(e))
            BaseModel( list)
        }
    }

    fun baseError1(error: ResponseBody?): BaseModel {
        return try {
            Gson().fromJson(error!!.charStream(), BaseModel::class.java)
        } catch (e: Exception) {
            var list= arrayListOf<String>()
            list.add(ResponseHandler.handleErrorResponse(e))
            BaseModel( list)
        }
    }

    fun <T> baseError(response: Response<T>): BaseModel {
        return try {
            return Gson().fromJson(response.body().toString()
                .takeIf { response.code() == Url.RESPONSE_OK || response.code() == Url.RESPONSE_CREATED }
                ?: response.errorBody()?.string(), BaseModel::class.java)
        } catch (e: Exception) {
            var list= arrayListOf<String>()
            list.add(ResponseHandler.handleErrorResponse(e))
            BaseModel( list)
        }
    }
}