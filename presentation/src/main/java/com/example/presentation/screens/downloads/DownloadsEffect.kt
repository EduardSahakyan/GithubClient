package com.example.presentation.screens.downloads

sealed interface DownloadsEffect {

    data object ShowErrorToast : DownloadsEffect

}