package com.example.presentation.screens.search

import com.example.domain.models.repo.RepoModel

sealed interface SearchIntent {

    data object Search : SearchIntent
    data class UpdateSearchQuery(val query: String) : SearchIntent
    data class DownloadRepo(val id: Long) : SearchIntent
    data object OpenDownloadedReposScreen : SearchIntent
    data class OpenRepoInBrowser(val repo: RepoModel) : SearchIntent
}