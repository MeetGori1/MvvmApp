package com.example.mvvmapp.api

import com.example.mvvmapp.model.BaseModel


sealed class Results<T>(val data: List<T>? =null, val errorMessage: BaseModel?=null) {
    class Loading<T>:Results<T>()
    class Success<T>(data: List<T>? =null):Results<T>(data = data)
    class Error<T>(errorMessage: BaseModel):Results<T>(errorMessage = errorMessage)
}