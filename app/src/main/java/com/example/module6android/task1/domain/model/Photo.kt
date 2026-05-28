package com.example.module6android.task1.domain.model

data class Photo(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val downloadUrl: String,
    val thumbnailUrl: String
)
