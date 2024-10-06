package com.example.presentation.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.presentation.R
import com.example.presentation.main.theme.GithubClientTheme
import com.example.presentation.navigation.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        isPermissionGranted.value = isGranted
    }

    private val isPermissionGranted = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setTheme(R.style.Theme_App_Starting)
            installSplashScreen()
        }
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GithubClient)
        requestWriteToDownloadsPermission()
        enableEdgeToEdge()
        setContent {
            if (isPermissionGranted.value) {
                GithubClientTheme {
                    AppNavGraph(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .statusBarsPadding()
                    )
                }
            } else {
                requestWriteToDownloadsPermission()
            }
        }
    }

    private fun requestWriteToDownloadsPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            permissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            isPermissionGranted.value = true
        }
    }

}
