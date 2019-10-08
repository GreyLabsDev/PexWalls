package com.greylabsdev.pexwalls.domain.usecase

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import com.greylabsdev.pexwalls.domain.LinkGenerator
import java.io.File

class PhotoDownloadingUseCase(private val context: Context) {
    private val linkGenerator = LinkGenerator()

    fun callManagerToDownloadPhoto(
        autror: String,
        postfix: String,
        baseLink: String,
        resolution: Pair<Int, Int>) {

        val downloadLink = Uri.parse(linkGenerator.generateLink(baseLink, resolution))
        val photoFile = File(context.getExternalFilesDir(null), "PexWalls")
        val downloadRequest = DownloadManager.Request(downloadLink)
            .setTitle("PexWalls")
            .setDescription("Downloading photo")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationUri(Uri.fromFile(photoFile))
            .setRequiresCharging(false)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val id = downloadManager.enqueue(downloadRequest)
    }
}