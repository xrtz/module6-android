package com.example.module6android.task3.data.repository

import com.example.module6android.task3.data.remote.DummyApi
import com.example.module6android.task3.domain.model.User
import com.example.module6android.task3.domain.repository.UserRepository

class UserRepositoryImpl(private val api: DummyApi) : UserRepository {
    override suspend fun getUsers(token: String): List<User> {
        return api.getUsers(token).users?.map { dto ->
            User(
                id = dto.id ?: 0,
                firstName = dto.firstName ?: "",
                lastName = dto.lastName ?: "",
                username = dto.username ?: "",
                email = dto.email ?: "",
                image = dto.image ?: ""
            )
        } ?: emptyList()
    }

    override suspend fun getUserById(id: Int, token: String): User {
        val dto = api.getUserById(id, token)
        return User(
            id = dto.id ?: 0,
            firstName = dto.firstName ?: "",
            lastName = dto.lastName ?: "",
            username = dto.username ?: "",
            email = dto.email ?: "",
            image = dto.image ?: ""
        )
    }
}
