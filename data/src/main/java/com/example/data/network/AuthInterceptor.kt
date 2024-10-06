package com.example.data.network

import com.example.data.datasources.auth.AuthLocalDataSource
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = authLocalDataSource.getToken()
        val request = token?.let { original.newBuilder().addHeader(HEADER_AUTH, "Bearer $it").build() } ?: original
        return chain.proceed(request)
    }

}