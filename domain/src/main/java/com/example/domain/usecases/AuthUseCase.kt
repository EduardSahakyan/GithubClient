package com.example.domain.usecases

import com.example.domain.Resource
import com.example.domain.base.BaseUseCase
import com.example.domain.models.auth.params.AuthParamModel
import com.example.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<AuthParamModel>() {

    override suspend fun doWork(param: AuthParamModel): Resource<Unit> {
        return authRepository.auth(
            token = param.token
        )
    }

}