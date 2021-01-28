package com.marvelapp06.marvelapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class CreatorSummary(
    val resourceURI: String?,
    val name: String?,
    val role: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(resourceURI)
        parcel.writeString(name)
        parcel.writeString(role)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CreatorSummary> {
        override fun createFromParcel(parcel: Parcel): CreatorSummary {
            return CreatorSummary(parcel)
        }

        override fun newArray(size: Int): Array<CreatorSummary?> {
            return arrayOfNulls(size)
        }
    }
}
