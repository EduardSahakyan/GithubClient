package com.example.data.utils

import com.example.domain.Resource
import com.example.domain.exceptions.InvalidTokenException
import retrofit2.Response

suspend fun<R, M> safeAuthApiCall(
    request: suspend () -> Response<R>,
    updateAuthState: (isSignedIn: Boolean) -> Unit,
    mapper: (R) -> M
): Resource<M> {
    val response = request.invoke()
    return if (response.isSuccessful) {
        updateAuthState.invoke(true)
        response.body()?.let { Resource.Success(mapper.invoke(it)) } ?: Resource.Error(Exception("Unknown Error"))
    } else {
        updateAuthState.invoke(false)
        Resource.Error(InvalidTokenException())
    }
}

suspend fun<R, M> safeApiCall(
    request: suspend () -> Response<R>,
    mapper: (R) -> M
): Resource<M> {
    val response = request.invoke()
    return if (response.isSuccessful) {
        response.body()?.let { Resource.Success(mapper.invoke(it)) } ?: Resource.Error(Exception("Unknown Error"))
    } else {
        Resource.Error(Exception("Unknown error"))
    }
}