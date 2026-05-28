package com.example.module6android.task3.data.repository

import com.example.module6android.task3.data.local.TokenDataStore
import com.example.module6android.task3.data.remote.DummyApi
import com.example.module6android.task3.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: DummyApi,
    private val dataStore: TokenDataStore
) : AuthRepository {
    override suspend fun login(username: String, password: String): String {
        val response = api.login(username, password)
        val token = response.accessToken ?: throw Exception("Неверные данные")
        dataStore.saveToken(token)
        return token
    }

    override suspend fun getToken(): String? = dataStore.getToken()

    override suspend fun clearToken() = dataStore.clearToken()
}
