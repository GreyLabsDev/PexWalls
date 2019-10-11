package com.greylabsdev.pexwalls.domain.usecase

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import com.greylabsdev.pexwalls.domain.tools.PhotoUrlGenerator
import com.greylabsdev.pexwalls.domain.tools.ResolutionManager
import java.io.File

class PhotoDownloadingUseCase(
    private val context: Context,
    private val resolutionManager: ResolutionManager
) {
    private val linkGenerator = PhotoUrlGenerator()

    fun callManagerToDownloadPhoto(
        author: String,
        postfix: String,
        baseLink: String): Long {

        val fileName = "${author}_${postfix}.jpeg"
        val downloadUrl = Uri.parse(linkGenerator.generateUrl(baseLink, resolutionManager.screenResolution))
        val photoFile = File(context.getExternalFilesDir(null), fileName)

        val downloadRequest = DownloadManager.Request(downloadUrl)
            .setTitle(fileName)
            .setDescription("downloading photo")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationUri(Uri.fromFile(photoFile))
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val id = downloadManager.enqueue(downloadRequest)
        return id
    }


}