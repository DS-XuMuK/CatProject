package com.example.catproject.presentation.utils

sealed class AppState<out T> {
    object Loading : AppState<Nothing>()
    data class Success<T>(val data: T) : AppState<T>()
    data class Error(val message: String) : AppState<Nothing>()
}