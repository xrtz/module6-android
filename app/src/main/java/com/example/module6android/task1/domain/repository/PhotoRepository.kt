package com.example.module6android.task1.domain.repository

import com.example.module6android.task1.domain.model.Photo

interface PhotoRepository {
    suspend fun getPhotos(): List<Photo>
}
