package com.example.module6android.task3.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val username: String? = null,
    val email: String? = null,
    val image: String? = null,
    val phone: String? = null,
    val age: Int? = null
)

@Serializable
data class UsersResponseDto(
    val users: List<UserDto>? = null,
    val total: Int? = null
)
