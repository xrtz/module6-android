package com.example.module6android.task6.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(val username: String, val password: String)

@Serializable
data class LoginResponseDto(val token: String, val username: String)

@Serializable
data class LaureateDto(
    val id: Int,
    val prizeId: Int,
    val fullName: String,
    val portion: String,
    val motivation: String,
    val portraitUrl: String = ""
)

@Serializable
data class PrizeDto(
    val id: Int,
    val awardYear: Int,
    val category: String,
    val fullName: String,
    val motivation: String = "",
    val detailLink: String = "",
    val laureates: List<LaureateDto> = emptyList()
)

@Serializable
data class FavoritePrizeDto(
    val prizeId: Int,
    val awardYear: Int,
    val category: String,
    val fullName: String,
    val motivation: String
)

@Serializable
data class UserProfileDto(
    val id: Int,
    val username: String,
    val role: String
)
