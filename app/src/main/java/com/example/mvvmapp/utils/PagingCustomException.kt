package com.example.mvvmapp.utils

import com.example.mvvmapp.model.BaseModel

class PagingCustomException(error: BaseModel?): Exception(error?.errors.toString())


