package com.example.presentation.navigation

sealed class Screen(val route: String) {

    data object Auth : Screen(ROUTE_AUTH)
    data object Search : Screen(ROUTE_SEARCH)
    data object Downloads : Screen(ROUTE_DOWNLOADS)

    companion object {
        private const val ROUTE_AUTH = "route_auth"
        private const val ROUTE_SEARCH = "route_search"
        private const val ROUTE_DOWNLOADS = "route_downloads"
    }
}