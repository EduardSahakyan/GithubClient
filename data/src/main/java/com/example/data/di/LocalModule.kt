package com.example.data.di

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.data.local.AppDatabase
import com.example.data.local.dao.RepoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("auth_pref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app.db").build()
    }

    @Provides
    @Singleton
    fun provideRepoDao(appDatabase: AppDatabase): RepoDao {
        return appDatabase.repoDao()
    }

}