package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Beer(
    val id: Long,
    val name: String,
    val tagline: String,
    @Json(name = "first_brewed") val firstBrewed: String? = null,
    val description: String,
    @Json(name = "image_url") val imageUrl: String?,
    @Json(name = "abv") val alcoholByVolume: Double,
    @Json(name = "ibu") val internationalBitternessUnits: Double?,
    @Json(name = "target_fg") val targetFg: Double? = null, // target final gravity
    @Json(name = "target_og") val targetOg: Double? = null, // target original gravity
    @Json(name = "ebc") val europeanBeerConventionColor: Double?,
    @Json(name = "srm") val standardReferenceMethod: Double? = null,
    val ph: Double? = null,
    @Json(name = "attenuation_level") val attenuationLevel: Double? = null,
    val volume: Measurable? = null,
    @Json(name = "boil_volume") val boilVolume: Measurable? = null,
    @Json(name = "brewers_tips") val brewersTip: String? = null,
    @Json(name = "contributed_by") val contributedBy: String? = null
){

    fun getABVIntegerValue() = alcoholByVolume.toInt()
    fun getIBUIntegerValue() = internationalBitternessUnits?.toInt()
    fun getEBCIntegerValue() = europeanBeerConventionColor?.toInt()
    fun getPHIntegerValue() = ph?.toInt()
    fun getFirstBrewedDate() = "First Brewed: " + firstBrewed
    fun getContributor() = "Contributed by " + contributedBy
}