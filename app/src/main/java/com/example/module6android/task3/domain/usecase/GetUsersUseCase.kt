package com.example.module6android.task3.domain.usecase

import com.example.module6android.task3.domain.model.User
import com.example.module6android.task3.domain.repository.UserRepository

class GetUsersUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(token: String): List<User> = repository.getUsers(token)
}
