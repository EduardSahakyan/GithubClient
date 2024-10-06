package com.example.domain.usecases

import com.example.domain.Resource
import com.example.domain.base.BaseResultEmptyParamUseCase
import com.example.domain.models.repo.DownloadedRepoModel
import com.example.domain.repositories.RepoRepository
import javax.inject.Inject

class GetDownloadedReposUseCase @Inject constructor(
    private val repoRepository: RepoRepository
) : BaseResultEmptyParamUseCase<List<DownloadedRepoModel>>() {

    override suspend fun doWork(): Resource<List<DownloadedRepoModel>> {
        return repoRepository.getDownloadedRepos()
    }

}