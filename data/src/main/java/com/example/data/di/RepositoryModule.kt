package com.example.data.di

import com.example.data.repositories.AuthRepositoryImpl
import com.example.data.repositories.RepoRepositoryImpl
import com.example.domain.repositories.AuthRepository
import com.example.domain.repositories.RepoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindRepoRepository(impl: RepoRepositoryImpl): RepoRepository

}