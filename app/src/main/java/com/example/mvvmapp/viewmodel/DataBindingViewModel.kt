package com.example.mvvmapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmapp.api.ParserHelper
import com.example.mvvmapp.api.Results
import com.example.mvvmapp.model.ImageItem
import com.example.mvvmapp.repository.QuoteRepository
import kotlinx.coroutines.launch

class DataBindingViewModel() : ViewModel() {
    var results = MutableLiveData("water")

    fun updateData() {
    results.value="you Are Water!!"
    }


}