package com.example.module6android.task1.domain.usecase

import com.example.module6android.task1.domain.model.Photo
import com.example.module6android.task1.domain.repository.PhotoRepository

class GetPhotosUseCase(private val repository: PhotoRepository) {
    suspend operator fun invoke(): List<Photo> = repository.getPhotos()
}
