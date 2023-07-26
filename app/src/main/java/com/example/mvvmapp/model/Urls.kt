package com.example.mvvmapp.model


data class ImageItem(
    val description: String,
    val id: String,
    val urls: Urls
)
data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
)
