package com.example.domain.repositories

import com.example.domain.Resource

interface AuthRepository {

    suspend fun auth(token: String): Resource<Unit>

    fun getAuthState(): Resource<Boolean>

}