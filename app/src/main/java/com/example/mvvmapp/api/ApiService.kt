package com.example.mvvmapp.api

import com.example.mvvmapp.model.ImageItem
import com.example.unsplashdemo.Utils.Constants
import com.example.unsplashdemo.Utils.Constants.END_POINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("${Constants.ACCEPT_VERSION}: ${Constants.VERSION}",
        "${Constants.AUTHORIZATION} ${Constants.CLIENT_ID}")
    @GET(END_POINT)
    suspend fun getData(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
    ): Response<List<ImageItem>>
}