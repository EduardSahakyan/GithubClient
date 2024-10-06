package com.example.data.repositories

import com.example.data.datasources.repo.RepoLocalDataSource
import com.example.data.datasources.repo.RepoRemoteDataSource
import com.example.data.local.entities.DownloadedRepoEntity
import com.example.data.mappers.toDownloadedRepos
import com.example.data.mappers.toRepos
import com.example.data.utils.safeApiCall
import com.example.domain.Resource
import com.example.domain.models.repo.DownloadedRepoModel
import com.example.domain.models.repo.RepoModel
import com.example.domain.repositories.RepoRepository
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val repoLocalDataSource: RepoLocalDataSource,
    private val repoRemoteDataSource: RepoRemoteDataSource,
) : RepoRepository {

    override suspend fun getUserRepos(username: String): Resource<List<RepoModel>> {
        return safeApiCall(
            request = { repoRemoteDataSource.getUserRepos(username) },
            mapper = { it.toRepos() }
        )
    }

    override suspend fun downloadRepo(owner: String, repo: String, id: Long): Resource<Unit> {
        val response = repoRemoteDataSource.downloadRepo(owner, repo)
        return if (response.isSuccessful) {
            response.body()?.byteStream()?.use { inputStream->
                repoLocalDataSource.saveFile(inputStream, "$owner-$repo.zip")
            } ?: Resource.Error(Exception("Impossible to download file"))
            val entity = DownloadedRepoEntity(id = id, name = repo, owner = owner)
            repoLocalDataSource.insertDownloadedRepo(entity)
            Resource.Success(Unit)
        } else {
            Resource.Error(Exception("Repository isn't found"))
        }
    }

    override suspend fun getDownloadedRepos(): Resource<List<DownloadedRepoModel>> {
        return Resource.Success(repoLocalDataSource.getDownloadedRepos().toDownloadedRepos())
    }

}