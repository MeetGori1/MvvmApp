package com.example.mvvmapp.model

import com.google.gson.annotations.SerializedName

open class BaseModel(
    @SerializedName("errors" ) var errors: ArrayList<String> = arrayListOf()
//    @SerializedName("message") var message: String
) {
    @SerializedName("code")
    var code: Int? = null
}