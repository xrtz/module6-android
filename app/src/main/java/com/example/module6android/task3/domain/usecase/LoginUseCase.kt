package com.example.module6android.task3.domain.usecase

import com.example.module6android.task3.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): String =
        repository.login(username, password)
}
