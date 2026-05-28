package com.example.module6android.task6.presentation.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.module6android.task6.data.repository.Task6RepositoryImpl
import com.example.module6android.task6.domain.model.FavoritePrize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class FavoriteState {
    object Idle : FavoriteState()
    object Loading : FavoriteState()
    data class Loaded(val isFavorite: Boolean) : FavoriteState()
    data class Error(val message: String) : FavoriteState()
}

class Task6PrizeDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _favoriteState = MutableStateFlow<FavoriteState>(FavoriteState.Idle)
    val favoriteState: StateFlow<FavoriteState> = _favoriteState

    private val repository = Task6RepositoryImpl()
    private var favorites: List<FavoritePrize> = emptyList()

    fun loadFavorites(token: String, prizeId: Int) {
        viewModelScope.launch {
            _favoriteState.value = FavoriteState.Loading
            try {
                favorites = repository.getFavorites(token)
                _favoriteState.value = FavoriteState.Loaded(favorites.any { it.prizeId == prizeId })
            } catch (e: Exception) {
                _favoriteState.value = FavoriteState.Error(e.message ?: "Ошибка")
            }
        }
    }

    fun toggleFavorite(token: String, prizeId: Int) {
        val current = favoriteState.value
        if (current !is FavoriteState.Loaded) return
        viewModelScope.launch {
            try {
                if (current.isFavorite) {
                    repository.removeFavorite(token, prizeId)
                    _favoriteState.value = FavoriteState.Loaded(false)
                } else {
                    repository.addFavorite(token, prizeId)
                    _favoriteState.value = FavoriteState.Loaded(true)
                }
            } catch (e: Exception) {
                _favoriteState.value = FavoriteState.Error(e.message ?: "Ошибка")
            }
        }
    }
}
