package com.example.biblimemoapp

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Nullable
import org.jetbrains.annotations.NotNull

/**
 *
 */
data class Ouvrage (
    var ouvrageId: Long = 0L,
    var titre: String,
    var auteur: String,
    var numTome: Int,
    var description: String,
    var miniature: Int,
    var format: String,
    var quantite: Int,
    var lu: Boolean,
    var favori: Boolean,
    @Nullable
    var listesPartage: List<String>,
    @Nullable
    var collection: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.createStringArrayList()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(ouvrageId)
        parcel.writeString(titre)
        parcel.writeString(auteur)
        parcel.writeInt(numTome)
        parcel.writeString(description)
        parcel.writeInt(miniature)
        parcel.writeString(format)
        parcel.writeInt(quantite)
        parcel.writeByte(if (lu) 1 else 0)
        parcel.writeByte(if (favori) 1 else 0)
        parcel.writeStringList(listesPartage)
        parcel.writeString(collection)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ouvrage> {
        override fun createFromParcel(parcel: Parcel): Ouvrage {
            return Ouvrage(parcel)
        }

        override fun newArray(size: Int): Array<Ouvrage?> {
            return arrayOfNulls(size)
        }
    }
}