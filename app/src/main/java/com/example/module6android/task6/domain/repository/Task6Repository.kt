package com.example.module6android.task6.domain.repository

import com.example.module6android.task6.domain.model.FavoritePrize
import com.example.module6android.task6.domain.model.Prize

interface Task6Repository {
    suspend fun login(username: String, password: String): String
    suspend fun getPrizes(token: String): List<Prize>
    suspend fun getPrize(token: String, year: Int, category: String): Prize
    suspend fun getFavorites(token: String): List<FavoritePrize>
    suspend fun addFavorite(token: String, prizeId: Int)
    suspend fun removeFavorite(token: String, prizeId: Int)
}
