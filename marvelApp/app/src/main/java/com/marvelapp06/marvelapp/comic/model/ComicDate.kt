package com.marvelapp06.marvelapp.comic.model

import android.os.Parcel
import android.os.ParcelFormatException
import android.os.Parcelable
import java.util.*

data class ComicDate(
    val type: String?,
    val date: Date?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Date::class.java.classLoader) as? Date
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type);
        parcel.writeValue(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComicDate> {
        override fun createFromParcel(parcel: Parcel): ComicDate {
            return ComicDate(parcel)
        }

        override fun newArray(size: Int): Array<ComicDate?> {
            return arrayOfNulls(size)
        }
    }
}