package com.example.data.datasources.auth

import com.example.data.network.api.UserService
import com.example.data.network.dtos.UserDto
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val userService: UserService
) {

    suspend fun getUser(): Response<UserDto> {
        return userService.getUser()
    }

}