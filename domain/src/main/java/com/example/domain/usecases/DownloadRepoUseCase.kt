package com.example.domain.usecases

import com.example.domain.Resource
import com.example.domain.base.BaseUseCase
import com.example.domain.models.repo.params.DownloadRepoParamModel
import com.example.domain.repositories.RepoRepository
import javax.inject.Inject

class DownloadRepoUseCase @Inject constructor(
    private val repoRepository: RepoRepository
) : BaseUseCase<DownloadRepoParamModel>() {

    override suspend fun doWork(param: DownloadRepoParamModel): Resource<Unit> {
        return repoRepository.downloadRepo(param.owner, param.repo, param.id)
    }

}