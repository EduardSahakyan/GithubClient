package com.example.presentation.screens.auth

import com.example.presentation.screens.auth.models.AuthErrorType

sealed interface AuthEffect {

    data class ShowErrorToast(val errorType: AuthErrorType) : AuthEffect

    data object NavigateToSearchScreen : AuthEffect

}