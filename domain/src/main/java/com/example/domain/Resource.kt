package com.example.domain

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()
    data class Success<T>(val model: T) : Resource<T>()
}