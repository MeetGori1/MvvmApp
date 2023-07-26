package com.example.mvvmapp.repository

import com.example.mvvmapp.api.RetrofitHelper
import com.example.mvvmapp.model.ImageItem
import retrofit2.Response

class QuoteRepository() {
     suspend fun getImages(page:Int,perPage:Int): Response<List<ImageItem>>{
        return RetrofitHelper.getInstance().getData(page,perPage)
    }
}