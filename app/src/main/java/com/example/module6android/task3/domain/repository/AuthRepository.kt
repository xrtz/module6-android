package com.example.module6android.task3.domain.repository

interface AuthRepository {
    suspend fun login(username: String, password: String): String
    suspend fun getToken(): String?
    suspend fun clearToken()
}
