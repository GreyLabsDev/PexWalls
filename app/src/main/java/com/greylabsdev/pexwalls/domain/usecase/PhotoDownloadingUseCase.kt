package com.greylabsdev.pexwalls.domain.usecase

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import com.greylabsdev.pexwalls.domain.tools.PhotoUrlGenerator
import com.greylabsdev.pexwalls.domain.tools.ResolutionManager
import com.greylabsdev.pexwalls.domain.tools.WallpaperSetter
import io.reactivex.Observable
import java.io.File
import java.net.URI
import java.util.concurrent.TimeUnit

class PhotoDownloadingUseCase(
    private val context: Context,
    private val resolutionManager: ResolutionManager,
    private val wallpaperSetter: WallpaperSetter
) {
    private val linkGenerator = PhotoUrlGenerator()

    fun callManagerToDownloadPhoto(
        author: String,
        postfix: String,
        baseLink: String,
        originalResolution: Pair<Int, Int>? = null,
        setAsWallpaper: Boolean = false): Observable<Int> {

        val fileName = "${author.replace(" ", "_")}_${postfix}.jpeg"
        val downloadUrl = Uri.parse(linkGenerator.generateUrl(
                baseLink, if (originalResolution != null) ResolutionManager.Resolution(originalResolution.first,
                originalResolution.second)
                    else resolutionManager.screenResolution
            )
        )
        val photoDirPath = Environment.getExternalStorageDirectory().toString() + "/PexWalls"
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
        val id = downloadManager.enqueue(downloadRequest)
        return createDownloadListenerObservable(id, photoFile.toURI(), setAsWallpaper)
    }

    private fun createDownloadListenerObservable(
        downloadId: Long,
        fileUri: URI,
        setAsWallpaper: Boolean
    ): Observable<Int> {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val query = DownloadManager.Query().apply {
            setFilterById(downloadId)
        }
        var cursor: Cursor? = null
        return Observable.interval(1, TimeUnit.SECONDS)
            .flatMap {
                cursor = downloadManager.query(query)
                cursor!!.moveToFirst()
                val bytesDownloaded = cursor!!.getInt(cursor!!.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                val bytesTotal = cursor!!.getInt(cursor!!.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                val progress = (bytesDownloaded * 100L / bytesTotal).toInt()
                Observable.just(progress)
            }
            .takeWhile { progress -> progress != 100 }
            .doOnComplete {
                if (setAsWallpaper) setImageAsWallpaper(fileUri)
                cursor?.close()
            }
    }

    private fun setImageAsWallpaper(fileUri: URI) {
        wallpaperSetter.setWallpaperByImagePath(fileUri)
    }
}