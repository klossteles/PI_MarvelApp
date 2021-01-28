package com.marvelapp06.marvelapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class ThumbnailModel(
    private val path: String?,
    private val extension: String?
): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    fun getImagePath(imageResolution: String? = "detail"): String {
        return "$path/$imageResolution.$extension".replace("http://", "https://")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(path)
        parcel.writeString(extension)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ThumbnailModel> {
        override fun createFromParcel(parcel: Parcel): ThumbnailModel {
            return ThumbnailModel(parcel)
        }

        override fun newArray(size: Int): Array<ThumbnailModel?> {
            return arrayOfNulls(size)
        }
    }
}