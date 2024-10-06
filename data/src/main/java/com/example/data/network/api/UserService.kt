package com.example.data.network.api

import com.example.data.network.ENDPOINT_USER
import com.example.data.network.dtos.UserDto
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET(ENDPOINT_USER)
    suspend fun getUser(): Response<UserDto>

}