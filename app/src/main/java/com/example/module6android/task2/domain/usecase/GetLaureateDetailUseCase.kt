package com.example.module6android.task2.domain.usecase

import com.example.module6android.task2.domain.model.LaureateDetail
import com.example.module6android.task2.domain.repository.NobelRepository

class GetLaureateDetailUseCase(private val repository: NobelRepository) {
    suspend operator fun invoke(id: String): LaureateDetail = repository.getLaureate(id)
}
