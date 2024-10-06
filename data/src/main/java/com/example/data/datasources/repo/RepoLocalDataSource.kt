package com.example.data.datasources.repo

import android.content.ContentResolver
import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.example.data.local.dao.RepoDao
import com.example.data.local.entities.DownloadedRepoEntity
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class RepoLocalDataSource @Inject constructor(
    private val contentResolver: ContentResolver,
    private val repoDao: RepoDao
) {

    fun saveFile(inputStream: InputStream, fileName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Code for Android 10 (API 29) and above
            saveFileWithMediaStore(inputStream, fileName)
        } else {
            // Code for Android 9 (API 28) and below
            saveFileDirectly(inputStream, fileName)
        }
    }

    suspend fun insertDownloadedRepo(entity: DownloadedRepoEntity) {
        repoDao.insertDownloadedRepo(entity)
    }

    suspend fun getDownloadedRepos(): List<DownloadedRepoEntity> {
        return repoDao.getDownloadedRepos()
    }

    private fun saveFileDirectly(inputStream: InputStream, fileName: String) {
        val destinationFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName).also {
            it.createNewFile()
        }
        destinationFile.outputStream().use { outputStream->
            writeFileContent(inputStream, outputStream)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveFileWithMediaStore(inputStream: InputStream, fileName: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, "application/zip")
            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }
        val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
            contentResolver.openOutputStream(it)?.use { outputStream ->
                writeFileContent(inputStream, outputStream)
            }
        }
    }

    private fun writeFileContent(inputStream: InputStream, outputStream: OutputStream) {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var progressBytes = 0L
        var bytes = inputStream.read(buffer)
        while (bytes >= 0) {
            outputStream.write(buffer, 0, bytes)
            progressBytes += bytes
            bytes = inputStream.read(buffer)
        }
    }

}