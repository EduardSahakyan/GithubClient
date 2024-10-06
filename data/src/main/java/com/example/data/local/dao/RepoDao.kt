package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entities.DownloadedRepoEntity

@Dao
interface RepoDao {

    @Query("SELECT * FROM downloaded_repos")
    suspend fun getDownloadedRepos(): List<DownloadedRepoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownloadedRepo(entity: DownloadedRepoEntity)

}