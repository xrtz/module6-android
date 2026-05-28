package com.example.module6android.task2.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.module6android.task2.data.remote.NobelApi
import com.example.module6android.task2.data.repository.NobelRepositoryImpl
import com.example.module6android.task2.domain.model.LaureateDetail
import com.example.module6android.task2.domain.usecase.GetLaureateDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NobelDetailViewModel : ViewModel() {

    private val useCase = GetLaureateDetailUseCase(NobelRepositoryImpl(NobelApi()))

    private val _laureates = MutableStateFlow<Map<String, LaureateDetail>>(emptyMap())
    val laureates: StateFlow<Map<String, LaureateDetail>> = _laureates

    fun loadLaureate(id: String) {
        if (id.isBlank() || _laureates.value.containsKey(id)) return
        viewModelScope.launch {
            try {
                val detail = useCase(id)
                _laureates.value = _laureates.value + (id to detail)
            } catch (_: Exception) {
            }
        }
    }
}
