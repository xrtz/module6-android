package com.example.module6android.task6.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.task6DataStore by preferencesDataStore(name = "task6_auth")

class Task6TokenStore(private val context: Context) {
    private val TOKEN_KEY = stringPreferencesKey("token")

    suspend fun saveToken(token: String) {
        context.task6DataStore.edit { it[TOKEN_KEY] = token }
    }

    suspend fun getToken(): String? = context.task6DataStore.data.first()[TOKEN_KEY]

    suspend fun clearToken() {
        context.task6DataStore.edit { it.remove(TOKEN_KEY) }
    }
}
