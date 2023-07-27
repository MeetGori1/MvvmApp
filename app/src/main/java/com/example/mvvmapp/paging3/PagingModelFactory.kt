package com.example.mvvmapp.paging3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PagingModelFactory(val page:Int, val perPage:Int):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PagingViewModel(page, perPage) as T
    }
}