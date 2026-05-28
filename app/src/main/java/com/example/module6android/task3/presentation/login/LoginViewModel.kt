package com.example.module6android.task3.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.module6android.task3.data.local.TokenDataStore
import com.example.module6android.task3.data.remote.DummyApi
import com.example.module6android.task3.data.repository.AuthRepositoryImpl
import com.example.module6android.task3.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state

    private val dataStore = TokenDataStore(application)
    private val useCase = LoginUseCase(AuthRepositoryImpl(DummyApi(), dataStore))

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            try {
                val token = useCase(username, password)
                _state.value = LoginState.Success(token)
            } catch (e: Exception) {
                _state.value = LoginState.Error(e.message ?: "Ошибка авторизации")
            }
        }
    }

    fun checkSavedToken(onToken: (String) -> Unit) {
        viewModelScope.launch {
            val token = dataStore.getToken()
            if (!token.isNullOrEmpty()) onToken(token)
        }
    }
}
