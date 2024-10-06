package com.example.presentation.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.R
import com.example.presentation.screens.auth.models.AuthErrorType
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToSearchScreen: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest {
            when (it) {
                AuthEffect.NavigateToSearchScreen -> navigateToSearchScreen()
                is AuthEffect.ShowErrorToast -> {
                    val messageId = when (it.errorType) {
                        AuthErrorType.INVALID_TOKEN -> R.string.invalid_token
                        AuthErrorType.CONNECTION -> R.string.connection_error
                    }
                    Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = state.value.token,
            onValueChange = {
                viewModel.handleIntent(AuthIntent.UpdateTokenInput(it))
            },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(.9F)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth(.5F)
                .height(48.dp)
                .padding(top = 8.dp),
            onClick = {
                viewModel.handleIntent(AuthIntent.Auth)
            },
            shape = AbsoluteRoundedCornerShape(4.dp)
        ) {
            if (state.value.isLoading) {
                CircularProgressIndicator(
                    color = Color.Cyan
                )
            } else {
                Text(text = stringResource(R.string.sign_in))
            }
        }
    }

}