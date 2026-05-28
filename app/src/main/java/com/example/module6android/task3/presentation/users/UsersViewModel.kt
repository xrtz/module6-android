package com.example.module6android.task3.presentation.users

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.module6android.task3.data.local.TokenDataStore
import com.example.module6android.task3.data.remote.DummyApi
import com.example.module6android.task3.data.repository.AuthRepositoryImpl
import com.example.module6android.task3.data.repository.UserRepositoryImpl
import com.example.module6android.task3.domain.model.User
import com.example.module6android.task3.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UsersState {
    object Loading : UsersState()
    data class Success(val users: List<User>) : UsersState()
    data class Error(val message: String) : UsersState()
}

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableStateFlow<UsersState>(UsersState.Loading)
    val state: StateFlow<UsersState> = _state

    private val api = DummyApi()
    private val dataStore = TokenDataStore(application)
    private val authRepo = AuthRepositoryImpl(api, dataStore)
    private val useCase = GetUsersUseCase(UserRepositoryImpl(api))

    fun loadUsers() {
        viewModelScope.launch {
            _state.value = UsersState.Loading
            try {
                val token = authRepo.getToken() ?: throw Exception("Нет токена")
                val users = useCase(token)
                _state.value = UsersState.Success(users)
            } catch (e: Exception) {
                _state.value = UsersState.Error(e.message ?: "Ошибка")
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
