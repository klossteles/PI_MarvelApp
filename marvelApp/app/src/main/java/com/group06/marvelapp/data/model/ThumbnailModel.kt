package com.group06.marvelapp.data.model

data class ThumbnailModel (
    private val path: String,
    private val extension: String
) {
    fun getImagePath(imageResolution: String? = "detail"): String {
        return "$path/$imageResolution.$extension".replace("http://", "https://")
    }
}