package com.example.module6android.task6.domain.repository

import com.example.module6android.task6.domain.model.FavoritePrize
import com.example.module6android.task6.domain.model.Prize

interface Task6Repository {
    suspend fun login(username: String, password: String): String
    suspend fun getPrizes(): List<Prize>
    suspend fun getFavorites(token: String): List<FavoritePrize>
    suspend fun addFavorite(token: String, prizeId: Int)
    suspend fun removeFavorite(token: String, prizeId: Int)
}
