package com.example.module6android.task1.presentation.list

import com.example.module6android.task1.domain.model.Photo

sealed class PhotoListState {
    object Loading : PhotoListState()
    data class Success(val photos: List<Photo>) : PhotoListState()
    data class Error(val message: String) : PhotoListState()
}
