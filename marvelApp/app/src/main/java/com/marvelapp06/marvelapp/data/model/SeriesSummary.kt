package com.marvelapp06.marvelapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class SeriesSummary(
    val resourceURI: String?,
    val name: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(resourceURI)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SeriesSummary> {
        override fun createFromParcel(parcel: Parcel): SeriesSummary {
            return SeriesSummary(parcel)
        }

        override fun newArray(size: Int): Array<SeriesSummary?> {
            return arrayOfNulls(size)
        }
    }
}
