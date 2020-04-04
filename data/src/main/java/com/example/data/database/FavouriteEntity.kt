package com.example.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_beers", primaryKeys = ["id", "owner_id"])
data class FavouriteEntity (
    val id: Long,
    @ColumnInfo(name = "fav_name")
    val name: String,
    @ColumnInfo(name = "fav_tagline")
    val tagline: String,
    @ColumnInfo(name = "fav_description")
    val description: String,
    @ColumnInfo(name = "fav_image_url")
    val imageUrl: String?,
    @ColumnInfo(name = "fav_abv")
    val alcoholByVolume: Double,
    @ColumnInfo(name = "fav_ibu")
    val internationalBitternessUnits: Double?,
    @ColumnInfo(name = "fav_ebc")
    val europeanBeerConventionColor: Double?,
    @ColumnInfo(name = "owner_id")
    var userId: String = ""
)