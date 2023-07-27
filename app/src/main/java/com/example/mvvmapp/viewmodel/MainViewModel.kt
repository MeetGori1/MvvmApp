package com.example.mvvmapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmapp.api.ParserHelper
import com.example.mvvmapp.api.Results
import com.example.mvvmapp.model.ImageItem
import com.example.mvvmapp.repository.QuoteRepository
import kotlinx.coroutines.launch

class MainViewModel(var page: Int,var perPage: Int) : ViewModel() {
    var results = MutableLiveData<Results<ImageItem>>()

    private val repository: QuoteRepository by lazy {
        QuoteRepository()
    }

    init {
        viewModelScope.launch {
            getQuotes(page,perPage)
        }
    }


    suspend fun getQuotes(page: Int, perPage: Int) {
        /*     val response = repository.getImages(page, perPage)
             val result = response.body()
             if (response.isSuccessful) {
                 if (result != null) this.emit(Results.Success<ImageItem>(result))
                 else this.emit(Results.Error<ImageItem>(ParserHelper.baseError(response.errorBody())))
             } else {
                 this.emit(Results.Error<ImageItem>(ParserHelper.baseError(response.errorBody())))
             }*/

        val response = repository.getImages(page, perPage)
        val result = response.body()
        if (response.isSuccessful) {
            if (result != null) results.postValue(Results.Success<ImageItem>(result))
            else results.postValue(Results.Error<ImageItem>(ParserHelper.baseError(response.errorBody())))
        } else {
            results.postValue(Results.Error<ImageItem>(ParserHelper.baseError(response.errorBody())))
        }
    }
}