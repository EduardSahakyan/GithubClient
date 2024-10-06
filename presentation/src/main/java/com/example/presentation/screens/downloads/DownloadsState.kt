package com.example.presentation.screens.downloads

import com.example.domain.models.repo.DownloadedRepoModel

data class DownloadsState(
    val isLoading: Boolean = false,
    val downloadedRepos: List<DownloadedRepoModel> = emptyList()
)