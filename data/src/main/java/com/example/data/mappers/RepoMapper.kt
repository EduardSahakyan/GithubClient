package com.example.data.mappers

import com.example.data.network.dtos.RepoDto
import com.example.domain.models.repo.RepoModel

fun List<RepoDto>.toRepos() = map { it.toRepo() }

fun RepoDto.toRepo() = RepoModel(id = id, name = name, ownerName = owner.login)