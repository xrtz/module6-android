package com.example.module6android.task3.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val username: String,
    val password: String,
    val expiresInMins: Int = 60
)

@Serializable
data class LoginResponseDto(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val id: Int? = null,
    val username: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val image: String? = null
)
