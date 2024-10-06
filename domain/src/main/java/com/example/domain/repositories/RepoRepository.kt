package com.example.domain.repositories

import com.example.domain.Resource
import com.example.domain.models.repo.DownloadedRepoModel
import com.example.domain.models.repo.RepoModel

interface RepoRepository {

    suspend fun getUserRepos(username: String): Resource<List<RepoModel>>

    suspend fun downloadRepo(owner: String, repo: String, id: Long): Resource<Unit>

    suspend fun getDownloadedRepos(): Resource<List<DownloadedRepoModel>>

}