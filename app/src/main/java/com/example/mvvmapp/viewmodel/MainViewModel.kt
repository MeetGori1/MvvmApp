package com.example.mvvmapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mvvmapp.api.ParserHelper
import com.example.mvvmapp.api.Results
import com.example.mvvmapp.model.ImageItem
import com.example.mvvmapp.repository.QuoteRepository

class MainViewModel() : ViewModel() {
    private val repository: QuoteRepository by lazy {
        QuoteRepository()
    }
    fun getQuotes(page: Int, perPage: Int) = liveData<Results<ImageItem>> {
        val response = repository.getImages(page, perPage)
        val result = response.body()
        if (response.isSuccessful) {
            if (result != null) this.emit(Results.Success<ImageItem>(result))
            else this.emit(Results.Error<ImageItem>(ParserHelper.baseError(response.errorBody())))
        } else {
            this.emit(Results.Error<ImageItem>(ParserHelper.baseError(response.errorBody())))
        }
    }
}