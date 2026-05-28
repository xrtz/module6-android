package com.example.module6android.task1.data.repository

import com.example.module6android.task1.data.remote.PicsumApi
import com.example.module6android.task1.domain.model.Photo
import com.example.module6android.task1.domain.repository.PhotoRepository

class PhotoRepositoryImpl(private val api: PicsumApi) : PhotoRepository {
    override suspend fun getPhotos(): List<Photo> {
        return api.getPhotos().map { dto ->
            Photo(
                id = dto.id,
                author = dto.author,
                width = dto.width,
                height = dto.height,
                downloadUrl = dto.downloadUrl,
                thumbnailUrl = "https://picsum.photos/id/${dto.id}/300/200"
            )
        }
    }
}
