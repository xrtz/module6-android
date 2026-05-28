package com.example.module6android.task2.data.remote

import com.example.module6android.task2.data.remote.dto.LaureateDetailDto
import com.example.module6android.task2.data.remote.dto.NobelPrizeResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class NobelApi {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }

    suspend fun getPrizes(
        limit: Int = 25,
        offset: Int = 0,
        year: String? = null,
        category: String? = null
    ): NobelPrizeResponse {
        return client.get("https://api.nobelprize.org/2.1/nobelPrizes") {
            parameter("limit", limit)
            parameter("offset", offset)
            year?.let { parameter("nobelPrizeYear", it) }
            category?.let { parameter("nobelPrizeCategory", it) }
        }.body()
    }

    suspend fun getLaureate(id: String): LaureateDetailDto {
        return client.get("https://api.nobelprize.org/2.1/laureate/$id").body()
    }
}
