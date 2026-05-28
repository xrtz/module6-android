package com.example.module6android.task1.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.module6android.task1.data.remote.PicsumApi
import com.example.module6android.task1.data.repository.PhotoRepositoryImpl
import com.example.module6android.task1.domain.usecase.GetPhotosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhotoListViewModel : ViewModel() {

    private val _state = MutableStateFlow<PhotoListState>(PhotoListState.Loading)
    val state: StateFlow<PhotoListState> = _state

    private val api: PicsumApi by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PicsumApi::class.java)
    }

    private val useCase = GetPhotosUseCase(PhotoRepositoryImpl(api))

    init {
        loadPhotos()
    }

    fun loadPhotos() {
        viewModelScope.launch {
            _state.value = PhotoListState.Loading
            try {
                val photos = useCase()
                _state.value = PhotoListState.Success(photos)
            } catch (e: Exception) {
                _state.value = PhotoListState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}
