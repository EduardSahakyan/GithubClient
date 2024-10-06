package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.RepoDao
import com.example.data.local.entities.DownloadedRepoEntity

@Database(entities = [DownloadedRepoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}