package com.example.presentation.screens.search

import com.example.domain.models.repo.RepoModel

data class SearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val isDownloading: Boolean = false,
    val repos: List<RepoModel> = emptyList()
)
