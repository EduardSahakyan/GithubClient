package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentation.screens.auth.AuthScreen
import com.example.presentation.screens.auth.AuthViewModel
import com.example.presentation.screens.downloads.DownloadsScreen
import com.example.presentation.screens.search.SearchScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Auth.route, modifier = modifier) {

        composable(route = Screen.Auth.route) {
            AuthScreen(
                navigateToSearchScreen = {
                    navController.navigate(Screen.Search.route) {
                        popUpTo(Screen.Auth.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.Search.route) {
            SearchScreen(
                navigateToDownloadsScreen = {
                    navController.navigate(Screen.Downloads.route)
                }
            )
        }

        composable(route = Screen.Downloads.route) {
            DownloadsScreen()
        }

    }

}