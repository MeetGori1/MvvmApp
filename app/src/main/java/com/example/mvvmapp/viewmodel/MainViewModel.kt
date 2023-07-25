package com.example.mvvmapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmapp.model.QuoteModel
import com.example.mvvmapp.repository.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    private val repository: QuoteRepository by lazy {
        QuoteRepository()
    }

    fun getQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getQuotes(1)
        }
    }

    fun getData():LiveData<QuoteModel?>{
        return repository.quotes
    }
}