package com.example.mvvmapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmapp.api.ApiService
import com.example.mvvmapp.api.RetrofitHelper
import com.example.mvvmapp.model.QuoteModel
import retrofit2.Response

class QuoteRepository() {
    suspend fun getQuotes(page: Int): Response<QuoteModel> {
        return RetrofitHelper.getInstance().getQuotes(page)
        /*if (response.isSuccessful) {
            val result = response.body()
            quotesLiveData.postValue(result)
        }*/
    }
}