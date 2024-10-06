package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_repos")
class DownloadedRepoEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val owner: String
)