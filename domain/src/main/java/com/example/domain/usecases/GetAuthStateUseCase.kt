package com.example.domain.usecases

import com.example.domain.Resource
import com.example.domain.base.BaseResultEmptyParamUseCase
import com.example.domain.repositories.AuthRepository
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseResultEmptyParamUseCase<Boolean>() {

    override suspend fun doWork(): Resource<Boolean> {
        return authRepository.getAuthState()
    }

}