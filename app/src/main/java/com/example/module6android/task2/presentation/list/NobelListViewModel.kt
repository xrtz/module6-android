package com.example.module6android.task2.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.module6android.task2.data.remote.NobelApi
import com.example.module6android.task2.data.repository.NobelRepositoryImpl
import com.example.module6android.task2.domain.usecase.GetNobelPrizesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NobelListViewModel : ViewModel() {

    private val _state = MutableStateFlow<NobelListState>(NobelListState.Loading)
    val state: StateFlow<NobelListState> = _state

    private val useCase = GetNobelPrizesUseCase(NobelRepositoryImpl(NobelApi()))

    val categories = listOf("", "physics", "chemistry", "medicine", "literature", "peace", "economics")
    val years = listOf("") + (2023 downTo 1901).map { it.toString() }

    private val _selectedYear = MutableStateFlow("")
    val selectedYear: StateFlow<String> = _selectedYear

    private val _selectedCategory = MutableStateFlow("")
    val selectedCategory: StateFlow<String> = _selectedCategory

    init {
        loadPrizes()
    }

    fun setYear(year: String) {
        _selectedYear.value = year
        loadPrizes()
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
        loadPrizes()
    }

    fun loadPrizes() {
        viewModelScope.launch {
            _state.value = NobelListState.Loading
            try {
                val prizes = useCase(
                    year = _selectedYear.value.ifEmpty { null },
                    category = _selectedCategory.value.ifEmpty { null }
                )
                _state.value = NobelListState.Success(prizes)
            } catch (e: Exception) {
                _state.value = NobelListState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }
}
