package com.example.presentation.screens.search

import com.example.domain.models.repo.RepoModel

sealed interface SearchEffect {

    data object ShowErrorToast : SearchEffect
    data object NavigateToDownloadsScreen : SearchEffect
    data class OpenRepoInBrowser(val repo: RepoModel) : SearchEffect

}