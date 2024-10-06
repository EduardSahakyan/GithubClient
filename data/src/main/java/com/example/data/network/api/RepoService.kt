package com.example.data.network.api

import com.example.data.network.ENDPOINT_DOWNLOAD_REPO
import com.example.data.network.ENDPOINT_GET_USER_REPOS
import com.example.data.network.PATH_DOWNLOAD_OWNER
import com.example.data.network.PATH_DOWNLOAD_REPO
import com.example.data.network.PATH_USERNAME
import com.example.data.network.dtos.RepoDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface RepoService {

    @GET(ENDPOINT_GET_USER_REPOS)
    suspend fun getUserRepos(@Path(PATH_USERNAME) username: String): Response<List<RepoDto>>

    @GET(ENDPOINT_DOWNLOAD_REPO)
    @Streaming
    suspend fun downloadRepo(@Path(PATH_DOWNLOAD_OWNER) owner: String, @Path(PATH_DOWNLOAD_REPO) repo: String): Response<ResponseBody>

}