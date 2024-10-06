package com.example.domain.base

import com.example.domain.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

abstract class BaseResultUseCase<PARAM, RESULT> {

    operator fun invoke(param: PARAM): Flow<Resource<RESULT>> = flow {
        emit(Resource.Loading)
        emit(doWork(param))
    }.catch {
        emit(Resource.Error(it))
    }

    protected abstract suspend fun doWork(param: PARAM): Resource<RESULT>

}

abstract class BaseUseCase<PARAM> {

    operator fun invoke(param: PARAM): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        emit(doWork(param))
    }.catch {
        emit(Resource.Error(it))
    }

    protected abstract suspend fun doWork(param: PARAM): Resource<Unit>

}

abstract class BaseResultEmptyParamUseCase<RESULT> {

    operator fun invoke(): Flow<Resource<RESULT>> = flow {
        emit(Resource.Loading)
        emit(doWork())
    }.catch {
        emit(Resource.Error(it))
    }

    protected abstract suspend fun doWork(): Resource<RESULT>

}