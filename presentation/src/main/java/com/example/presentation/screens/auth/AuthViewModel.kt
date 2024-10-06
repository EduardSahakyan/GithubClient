package com.example.presentation.screens.auth

import androidx.lifecycle.viewModelScope
import com.example.domain.Resource
import com.example.domain.exceptions.InvalidTokenException
import com.example.domain.models.auth.params.AuthParamModel
import com.example.domain.usecases.AuthUseCase
import com.example.domain.usecases.GetAuthStateUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.screens.auth.models.AuthErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val getAuthStateUseCase: GetAuthStateUseCase
): BaseViewModel<AuthIntent, AuthState, AuthEffect>(
    initialState = AuthState()
) {

    init {
        checkAuthState()
    }

    override fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.Auth -> auth()
            is AuthIntent.UpdateTokenInput -> updateTokenInput(intent.newToken)
        }
    }

    private fun auth() {
        if (currentState.isLoading) return
        val param = AuthParamModel(currentState.token)
        authUseCase(param)
            .flowOn(Dispatchers.IO)
            .onEach { resource ->
                when (resource) {
                    Resource.Loading -> updateState { it.copy(isLoading = true) }
                    is Resource.Error -> {
                        val errorType = when (resource.throwable) {
                            is InvalidTokenException -> AuthErrorType.INVALID_TOKEN
                            else -> AuthErrorType.CONNECTION
                        }
                        sendEffect(AuthEffect.ShowErrorToast(errorType))
                    }
                    is Resource.Success -> sendEffect(AuthEffect.NavigateToSearchScreen)
                }
            }
            .onCompletion { updateState { it.copy(isLoading = false) } }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    private fun updateTokenInput(newToken: String) = updateState { it.copy(token = newToken) }

    private fun checkAuthState() {
        getAuthStateUseCase()
            .flowOn(Dispatchers.IO)
            .onEach { resource ->
                when (resource) {
                    Resource.Loading -> updateState { it.copy(isLoading = true) }
                    is Resource.Error -> Unit
                    is Resource.Success -> if (resource.model) sendEffect(AuthEffect.NavigateToSearchScreen)
                }
            }
            .onCompletion { updateState { it.copy(isLoading = false) } }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

}