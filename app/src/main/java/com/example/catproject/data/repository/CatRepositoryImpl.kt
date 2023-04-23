package com.example.catproject.data.repository

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.example.catproject.data.database.CatDao
import com.example.catproject.data.mapper.toData
import com.example.catproject.data.mapper.toDomain
import com.example.catproject.data.service.CatService
import com.example.catproject.domain.model.Cat
import com.example.catproject.domain.repository.CatRepository
import java.io.File
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val catService: CatService,
    private val catDao: CatDao,
    private val context: Context
) : CatRepository {

    override suspend fun getCatsFromInternet() =
        catService.getCatsList()

    override suspend fun getCatsFromDatabase() =
        catDao.getAllCats().map { it.toDomain() }

    override suspend fun saveCatToFavorites(cat: Cat) {
        catDao.addCat(cat.toData())
    }

    override suspend fun deleteCatFromFavorites(cat: Cat) {
        catDao.deleteCat(cat.toData())
    }

    override suspend fun downloadCat(cat: Cat): Long {
        val request = DownloadManager.Request(Uri.parse(cat.url))
        request.setTitle(cat.id)
            .setDescription("$DOWNLOADING_TEXT ${cat.id}")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                File.separator + cat.id
            )
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        return downloadManager.enqueue(request)
    }

    companion object {
        private const val DOWNLOADING_TEXT = "Downloading"
    }
}