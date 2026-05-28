package com.example.module6android.task2.presentation.list

import com.example.module6android.task2.domain.model.NobelPrize

sealed class NobelListState {
    object Loading : NobelListState()
    data class Success(val prizes: List<NobelPrize>) : NobelListState()
    data class Error(val message: String) : NobelListState()
}
