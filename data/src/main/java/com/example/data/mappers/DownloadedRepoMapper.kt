package com.example.data.mappers

import com.example.data.local.entities.DownloadedRepoEntity
import com.example.domain.models.repo.DownloadedRepoModel

fun List<DownloadedRepoEntity>.toDownloadedRepos() = map { it.toDownloadedRepo() }

fun DownloadedRepoEntity.toDownloadedRepo() = DownloadedRepoModel(id, name, owner)