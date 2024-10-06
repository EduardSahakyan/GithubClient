package com.example.data.datasources.repo

import com.example.data.network.api.RepoService
import com.example.data.network.dtos.RepoDto
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class RepoRemoteDataSource @Inject constructor(
    private val repoService: RepoService
) {

    suspend fun getUserRepos(username: String): Response<List<RepoDto>> {
        return repoService.getUserRepos(username)
    }

    suspend fun downloadRepo(owner: String, repo: String): Response<ResponseBody> {
        return repoService.downloadRepo(owner, repo)
    }

}