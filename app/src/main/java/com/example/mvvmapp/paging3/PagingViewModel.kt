package com.example.mvvmapp.paging3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.mvvmapp.api.ParserHelper
import com.example.mvvmapp.api.Results
import com.example.mvvmapp.model.ImageItem
import com.example.mvvmapp.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PagingViewModel(var page: Int, var perPage: Int) : ViewModel() {
    var results = MutableLiveData<PagingData<ImageItem>>()

    val repository: QuoteRepository by lazy {
        QuoteRepository()
    }

    fun getImages() {
        viewModelScope.launch {
            Pager(PagingConfig(pageSize = perPage, enablePlaceholders = false)) {
                CharactersPagingDataSource(repository, perPage)
            }.flow.cachedIn(viewModelScope).collect {
                results.postValue(it)
            }
        }
    }

//    val images: LiveData<PagingData<ImageItem>> = Pager(PagingConfig(pageSize = 20)) {
//        CharactersPagingDataSource(repository)
//    }.liveData.cachedIn(viewModelScope)
}