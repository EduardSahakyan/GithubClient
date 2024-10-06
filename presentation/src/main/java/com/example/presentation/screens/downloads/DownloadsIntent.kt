package com.example.presentation.screens.downloads

sealed interface DownloadsIntent {

    data object GetDownloadedRepos : DownloadsIntent

}