package com.example.module6android.task3.presentation.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.module6android.task3.data.local.TokenDataStore
import com.example.module6android.task3.data.remote.DummyApi
import com.example.module6android.task3.data.repository.AuthRepositoryImpl
import com.example.module6android.task3.data.repository.UserRepositoryImpl
import com.example.module6android.task3.domain.model.User
import com.example.module6android.task3.domain.usecase.GetUserDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UserDetailState {
    object Loading : UserDetailState()
    data class Success(val user: User) : UserDetailState()
    data class Error(val message: String) : UserDetailState()
}

class UserDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableStateFlow<UserDetailState>(UserDetailState.Loading)
    val state: StateFlow<UserDetailState> = _state

    private val api = DummyApi()
    private val dataStore = TokenDataStore(application)
    private val authRepo = AuthRepositoryImpl(api, dataStore)
    private val useCase = GetUserDetailUseCase(UserRepositoryImpl(api))

    fun loadUser(id: Int) {
        viewModelScope.launch {
            _state.value = UserDetailState.Loading
            try {
                val token = authRepo.getToken() ?: throw Exception("Нет токена")
                val user = useCase(id, token)
                _state.value = UserDetailState.Success(user)
            } catch (e: Exception) {
                _state.value = UserDetailState.Error(e.message ?: "Ошибка")
            }
        }
    }

    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            authRepo.clearToken()
            onLogout()
        }
    }
}
