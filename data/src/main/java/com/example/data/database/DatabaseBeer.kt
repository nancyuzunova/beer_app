package com.example.data.database

import androidx.room.*
import com.example.data.model.Beer
import com.example.data.model.Measurable

@Entity(tableName = "beer_table")
data class DatabaseBeer(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "tagline")
    val tagline: String,
    @ColumnInfo(name = "first_brewed")
    val firstBrewed: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "abv")
    val alcoholByVolume: Double,
    @ColumnInfo(name = "ibu")
    val internationalBitternessUnits: Double,
    @ColumnInfo(name = "target_fg")
    val targetFg: Double, // target final gravity
    @ColumnInfo(name = "target_og")
    val targetOg: Double, // target original gravity
    @ColumnInfo(name = "ebc")
    val europeanBeerConventionColor: Double,
    @ColumnInfo(name = "srm")
    val standardReferenceMethod: Double,
    @ColumnInfo(name = "ph")
    val ph: Double,
    @ColumnInfo(name = "attenuation_level")
    val attenuationLevel: Double,
    @Embedded(prefix = "volume_")
    val volume: Measurable,
    @Embedded(prefix = "boil_")
    val boilVolume: Measurable,
    @ColumnInfo(name = "brewers_tip")
    val brewersTip: String,
    @ColumnInfo(name = "contributed_by")
    val contributedBy: String)

//TODO include the method, list of ingredients and list of foods
