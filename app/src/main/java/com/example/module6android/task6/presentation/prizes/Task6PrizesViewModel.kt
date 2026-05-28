package com.example.module6android.task6.presentation.prizes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.module6android.task6.data.local.Task6TokenStore
import com.example.module6android.task6.data.repository.Task6RepositoryImpl
import com.example.module6android.task6.domain.model.Prize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class Task6PrizesState {
    object Loading : Task6PrizesState()
    data class Success(val prizes: List<Prize>) : Task6PrizesState()
    data class Error(val message: String) : Task6PrizesState()
}

class Task6PrizesViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow<Task6PrizesState>(Task6PrizesState.Loading)
    val state: StateFlow<Task6PrizesState> = _state

    private val repository = Task6RepositoryImpl()
    private val tokenStore = Task6TokenStore(application)

    fun loadPrizes(token: String) {
        viewModelScope.launch {
            _state.value = Task6PrizesState.Loading
            try {
                val prizes = repository.getPrizes(token)
                _state.value = Task6PrizesState.Success(prizes)
            } catch (e: Exception) {
                _state.value = Task6PrizesState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            tokenStore.clearToken()
            onLoggedOut()
        }
    }
}
