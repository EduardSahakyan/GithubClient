package com.example.presentation.screens.auth

sealed interface AuthIntent {

    data object Auth : AuthIntent
    data class UpdateTokenInput(val newToken: String) : AuthIntent
}