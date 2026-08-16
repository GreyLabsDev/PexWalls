package com.greylabsdev.pexwalls.domain.usecase

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.greylabsdev.pexwalls.domain.tools.PhotoUrlGenerator
import com.greylabsdev.pexwalls.domain.tools.ResolutionManager
import com.greylabsdev.pexwalls.domain.tools.WallpaperSetter
import java.io.File
import java.net.URI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PhotoDownloadingUseCase(
    private val context: Context,
    private val resolutionManager: ResolutionManager,
    private val wallpaperSetter: WallpaperSetter
) {
    private val linkGenerator = PhotoUrlGenerator()

    fun callManagerToDownloadPhotoByFlow(
        author: String,
        postfix: String,
        baseLink: String,
        originalResolution: Pair<Int, Int>? = null,
        setAsWallpaper: Boolean = false
    ): Flow<Int> {
        val fileName = "${author.replace(" ", "_")}_$postfix.jpeg"

        val downloadUrl = linkGenerator.generateUrl(
            baseLink,
            if (originalResolution != null) {
                ResolutionManager.Resolution(
                    originalResolution.first,
                    originalResolution.second
                )
            } else {
                resolutionManager.screenResolution
            }
        ).toUri()

        val photoDirPath = context.getExternalFilesDir(
            Environment.DIRECTORY_DOWNLOADS
        ).toString() + "/PexWalls"

        val photoDir = File(photoDirPath)
        if (photoDir.exists().not()) photoDir.mkdirs()
        val photoFile = File(photoDir, fileName)

        val downloadRequest = DownloadManager.Request(downloadUrl)
            .setTitle(fileName)
            .setDescription("downloading photo")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationUri(Uri.fromFile(photoFile))
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(downloadRequest)
        return buildProgressFlow(downloadManager, downloadId, photoFile.toURI(), setAsWallpaper)
    }

    private fun buildProgressFlow(
        downloadManager: DownloadManager,
        downloadId: Long,
        fileUri: URI,
        setAsWallpaper: Boolean
    ): Flow<Int> {
        val query = DownloadManager.Query().setFilterById(downloadId)
        return flow {
            var progress = 0
            emit(progress)
            while (progress < 100) {
                val cursor = downloadManager.query(query)
                cursor.use {
                    if (!it.moveToFirst()) {
                        delay(POLL_DELAY_MS)
                        return@use
                    }
                    val bytesDownloaded = it.getLong(
                        it.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                    )
                    val bytesTotal = it.getLong(
                        it.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                    )
                    progress = if (bytesTotal > 0) {
                        ((bytesDownloaded * 100) / bytesTotal).toInt().coerceIn(0, 100)
                    } else {
                        0
                    }
                }
                if (progress >= 100) {
                    if (setAsWallpaper) setImageAsWallpaper(fileUri)
                    emit(100)
                    break
                }
                emit(progress)
                delay(POLL_DELAY_MS)
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun setImageAsWallpaper(fileUri: URI) {
        wallpaperSetter.setWallpaperByImagePath(fileUri)
    }

    private companion object {
        const val POLL_DELAY_MS = 200L
    }
}
