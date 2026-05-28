package com.example.module6android.task2.domain.repository

import com.example.module6android.task2.domain.model.LaureateDetail
import com.example.module6android.task2.domain.model.NobelPrize

interface NobelRepository {
    suspend fun getPrizes(year: String? = null, category: String? = null): List<NobelPrize>
    suspend fun getLaureate(id: String): LaureateDetail
}
