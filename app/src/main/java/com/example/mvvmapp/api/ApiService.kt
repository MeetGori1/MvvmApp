package com.example.mvvmapp.api

import com.example.mvvmapp.model.QuoteModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("quotes")
    suspend fun getQuotes(@Query("page") page: Int): Response<QuoteModel>
}