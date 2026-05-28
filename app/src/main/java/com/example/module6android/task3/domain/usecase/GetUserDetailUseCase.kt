package com.example.module6android.task3.domain.usecase

import com.example.module6android.task3.domain.model.User
import com.example.module6android.task3.domain.repository.UserRepository

class GetUserDetailUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(id: Int, token: String): User = repository.getUserById(id, token)
}
