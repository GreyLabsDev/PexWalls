package com.greylabsdev.pexwalls.domain

class LinkGenerator {

    fun generateLink(
        baseLink: String,
        screenResolution: Pair<Int, Int>? = null,
        fullPhotoResolution: Pair<Int, Int>? = null
    ): String {
        val baseUrl = baseLink.split("?auto").first()
        val cropParameter = "?fit=crop"
        var sizeParameter = ""
        screenResolution?.let {
            sizeParameter = "&h=${it.second}&w=${it.first}"
        } ?: run {
            fullPhotoResolution?.let {
                sizeParameter = "&h=${it.second}&w=${it.first}"
            }
        }
        return if (sizeParameter.isBlank()) baseUrl
                else baseUrl+cropParameter+sizeParameter
    }
}