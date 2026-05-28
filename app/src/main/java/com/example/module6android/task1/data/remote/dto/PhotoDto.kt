package com.example.module6android.task1.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerializedName("download_url") val downloadUrl: String
)
