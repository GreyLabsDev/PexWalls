package com.greylabsdev.pexwalls.domain.tools

class PhotoUrlGenerator() {

    fun generateUrl(
        sourceUrl: String,
        photoResolution: ResolutionManager.Resolution? = null
    ): String {
        return generateUrlByResolution(sourceUrl, photoResolution)
    }

    private fun generateUrlByResolution(
        sourceUrl: String,
        photoResolution: ResolutionManager.Resolution? = null): String {
        val baseUrl = sourceUrl.split("?auto").first()
        val cropParameter = "?fit=crop"
        var sizeParameter = ""
        photoResolution?.let {
            sizeParameter = "&h=${it.height}&w=${it.width}"
        }
        return if (sizeParameter.isNotBlank()) {
            baseUrl + cropParameter + sizeParameter
        } else sourceUrl
    }
}