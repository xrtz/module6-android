package com.example.module6android.task3.domain.repository

import com.example.module6android.task3.domain.model.User

interface UserRepository {
    suspend fun getUsers(token: String): List<User>
    suspend fun getUserById(id: Int, token: String): User
}
