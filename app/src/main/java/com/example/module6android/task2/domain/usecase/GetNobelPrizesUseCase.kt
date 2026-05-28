package com.example.module6android.task2.domain.usecase

import com.example.module6android.task2.domain.model.NobelPrize
import com.example.module6android.task2.domain.repository.NobelRepository

class GetNobelPrizesUseCase(private val repository: NobelRepository) {
    suspend operator fun invoke(year: String? = null, category: String? = null): List<NobelPrize> =
        repository.getPrizes(year, category)
}
