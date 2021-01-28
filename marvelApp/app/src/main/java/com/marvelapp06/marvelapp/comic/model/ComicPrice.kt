package com.marvelapp06.marvelapp.comic.model

import android.os.Parcel
import android.os.Parcelable


data class ComicPrice (
    val type: String?,
    val price: Float?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Float::class.java.classLoader) as? Float
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeValue(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComicPrice> {
        override fun createFromParcel(parcel: Parcel): ComicPrice {
            return ComicPrice(parcel)
        }

        override fun newArray(size: Int): Array<ComicPrice?> {
            return arrayOfNulls(size)
        }
    }
}



