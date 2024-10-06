package com.example.presentation.screens.search.utils

import androidx.compose.ui.platform.UriHandler
import com.example.domain.models.repo.RepoModel

fun openRepoInBrowser(uriHandler: UriHandler, repo: RepoModel) {
    uriHandler.openUri("https://github.com/${repo.ownerName}/${repo.name}")
}