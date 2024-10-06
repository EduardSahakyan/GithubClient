package com.example.data.repositories

import com.example.data.datasources.auth.AuthLocalDataSource
import com.example.data.datasources.auth.AuthRemoteDataSource
import com.example.data.utils.safeAuthApiCall
import com.example.domain.Resource
import com.example.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
): AuthRepository {

    override suspend fun auth(token: String): Resource<Unit> {
        authLocalDataSource.changeToken(token)
        return safeAuthApiCall(
            request = { authRemoteDataSource.getUser() },
            updateAuthState = ::updateAuthState,
            mapper = {}
        )
    }

    override fun getAuthState(): Resource<Boolean> {
        return Resource.Success(authLocalDataSource.isSignedIn())
    }

    private fun updateAuthState(isSignedIn: Boolean) {
        authLocalDataSource.changeAuthState(isSignedIn)
        if (isSignedIn.not()) {
            authLocalDataSource.changeToken(null)
        }
    }

}