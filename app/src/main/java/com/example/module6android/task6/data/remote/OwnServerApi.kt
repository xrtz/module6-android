package com.example.module6android.task6.data.remote

import com.example.module6android.task6.data.remote.dto.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class OwnServerApi(private val baseUrl: String = BASE_URL) {
    companion object {
        // Замени на IP своего сервера, например "http://192.168.1.100:8080"
        const val BASE_URL = "http://10.0.2.2:8080"
    }

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
        return client.post("$baseUrl/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequestDto(username, password))
        }.body()
    }

    suspend fun getPrizes(token: String): List<PrizeDto> {
        return client.get("$baseUrl/prizes") {
            header("Authorization", "Bearer $token")
        }.body()
    }

    suspend fun getPrize(token: String, year: Int, category: String): PrizeDto {
        return client.get("$baseUrl/prizes/$year/$category") {
            header("Authorization", "Bearer $token")
        }.body()
    }

    suspend fun getFavorites(token: String): List<FavoritePrizeDto> {
        return client.get("$baseUrl/users/me/prizes") {
            header("Authorization", "Bearer $token")
        }.body()
    }

    suspend fun addFavorite(token: String, prizeId: Int) {
        client.post("$baseUrl/users/me/prizes/$prizeId") {
            header("Authorization", "Bearer $token")
        }
    }

    suspend fun removeFavorite(token: String, prizeId: Int) {
        client.delete("$baseUrl/users/me/prizes/$prizeId") {
            header("Authorization", "Bearer $token")
        }
    }
}
