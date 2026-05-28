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
    private val repository = Task6RepositoryImpl()
    private val tokenStore = Task6TokenStore(application)

    private var allPrizes: List<Prize> = emptyList()

    private val _state = MutableStateFlow<Task6PrizesState>(Task6PrizesState.Loading)
    val state: StateFlow<Task6PrizesState> = _state

    private val _selectedYear = MutableStateFlow("")
    val selectedYear: StateFlow<String> = _selectedYear

    private val _selectedCategory = MutableStateFlow("")
    val selectedCategory: StateFlow<String> = _selectedCategory

    val categories: List<String>
        get() = listOf("") + allPrizes.map { it.category }.distinct().sorted()

    val years: List<String>
        get() = listOf("") + allPrizes.map { it.awardYear.toString() }.distinct().sortedDescending()

    fun loadPrizes() {
        viewModelScope.launch {
            _state.value = Task6PrizesState.Loading
            try {
                allPrizes = repository.getPrizes()
                _state.value = Task6PrizesState.Success(applyFilter())
            } catch (e: Exception) {
                _state.value = Task6PrizesState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun setYear(year: String) {
        _selectedYear.value = year
        _state.value = Task6PrizesState.Success(applyFilter())
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
        _state.value = Task6PrizesState.Success(applyFilter())
    }

    private fun applyFilter(): List<Prize> {
        val year = _selectedYear.value
        val category = _selectedCategory.value
        return allPrizes.filter {
            (year.isEmpty() || it.awardYear.toString() == year) &&
                (category.isEmpty() || it.category.equals(category, ignoreCase = true))
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            tokenStore.clearToken()
            onLoggedOut()
        }
    }
}
