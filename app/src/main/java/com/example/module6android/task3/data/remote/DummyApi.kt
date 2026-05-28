package com.example.module6android.task3.data.remote

import com.example.module6android.task3.data.remote.dto.LoginRequestDto
import com.example.module6android.task3.data.remote.dto.LoginResponseDto
import com.example.module6android.task3.data.remote.dto.UserDto
import com.example.module6android.task3.data.remote.dto.UsersResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class DummyApi {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true; coerceInputValues = true })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }

    suspend fun login(username: String, password: String): LoginResponseDto {
        return client.post("https://dummyjson.com/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequestDto(username = username, password = password))
        }.body()
    }

    suspend fun getUsers(token: String): UsersResponseDto {
        return client.get("https://dummyjson.com/users") {
            header("Authorization", "Bearer $token")
        }.body()
    }

    suspend fun getUserById(id: Int, token: String): UserDto {
        return client.get("https://dummyjson.com/users/$id") {
            header("Authorization", "Bearer $token")
        }.body()
    }
}
