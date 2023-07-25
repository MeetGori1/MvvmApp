package com.example.mvvmapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmapp.model.ErrorMessage
import com.example.mvvmapp.model.QuoteModel
import com.example.mvvmapp.repository.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    private val repository: QuoteRepository by lazy {
        QuoteRepository()
    }

    val getQuotes = liveData<ErrorMessage?> {
        val response = repository.getQuotes(1, )

        if (response.isSuccessful) {
            val result = response.body()
            if (result != null) this.emit(result)
            else this.emit(ErrorMessage(message = "Response is empty"))
        } else {
            this.emit(ErrorMessage(message = "Network call failed"))
        }
    }


}