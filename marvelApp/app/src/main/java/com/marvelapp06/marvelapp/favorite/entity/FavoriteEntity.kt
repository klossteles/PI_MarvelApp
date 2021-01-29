package com.marvelapp06.marvelapp.favorite.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FAVORITE")

data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo
    var userId: String,
    @ColumnInfo
    var modelId: Int,
    @ColumnInfo
    var favorite: String,
    @ColumnInfo
    var category: Int
)