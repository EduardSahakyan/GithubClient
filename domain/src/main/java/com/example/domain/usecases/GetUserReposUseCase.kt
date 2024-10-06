package com.example.domain.usecases

import com.example.domain.Resource
import com.example.domain.base.BaseResultUseCase
import com.example.domain.models.repo.RepoModel
import com.example.domain.models.repo.params.SearchRepoParamModel
import com.example.domain.repositories.RepoRepository
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
    private val repoRepository: RepoRepository
) : BaseResultUseCase<SearchRepoParamModel, List<RepoModel>>() {

    override suspend fun doWork(param: SearchRepoParamModel): Resource<List<RepoModel>> {
        return repoRepository.getUserRepos(param.query)
    }

}