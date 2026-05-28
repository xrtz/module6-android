package com.example.module6android.task1.data.remote

import com.example.module6android.task1.data.remote.dto.PhotoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumApi {
    @GET("v2/list")
    suspend fun getPhotos(
        @Query("limit") limit: Int = 30,
        @Query("page") page: Int = 1
    ): List<PhotoDto>
}
