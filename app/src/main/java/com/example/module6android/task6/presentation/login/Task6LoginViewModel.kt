package com.example.module6android.task6.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.module6android.task6.data.local.Task6TokenStore
import com.example.module6android.task6.data.remote.OwnServerApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class Task6LoginState {
    object Idle : Task6LoginState()
    object Loading : Task6LoginState()
    data class Success(val token: String) : Task6LoginState()
    data class Error(val message: String) : Task6LoginState()
}

class Task6LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow<Task6LoginState>(Task6LoginState.Idle)
    val state: StateFlow<Task6LoginState> = _state

    private val api = OwnServerApi()
    private val tokenStore = Task6TokenStore(application)

    fun checkSavedToken(onToken: (String) -> Unit) {
        viewModelScope.launch {
            val t = tokenStore.getToken()
            if (!t.isNullOrEmpty()) onToken(t)
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.value = Task6LoginState.Loading
            try {
                val resp = api.login(username, password)
                tokenStore.saveToken(resp.token)
                _state.value = Task6LoginState.Success(resp.token)
            } catch (e: Exception) {
                _state.value = Task6LoginState.Error(e.message ?: "Ошибка подключения к серверу")
            }
        }
    }
}
