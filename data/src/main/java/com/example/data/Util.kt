package com.example.data

import com.example.data.database.DatabaseBeer
import com.example.data.database.FavouriteEntity
import com.example.data.model.Beer
import com.example.data.model.Measurable

fun DatabaseBeer.asDomainModel(): Beer {
    return Beer(
        id = this.id,
        name = this.name,
        tagline = this.tagline,
        firstBrewed = this.firstBrewed,
        description = this.description,
        imageUrl = this.imageUrl,
        alcoholByVolume = this.alcoholByVolume,
        internationalBitternessUnits = this.internationalBitternessUnits,
        targetFg = this.targetFg,
        targetOg = this.targetOg,
        europeanBeerConventionColor = this.europeanBeerConventionColor,
        standardReferenceMethod = this.standardReferenceMethod,
        ph = this.ph,
        attenuationLevel = this.attenuationLevel,
        volume = this.volume,
        boilVolume = this.boilVolume,
        brewersTip = this.brewersTip,
        contributedBy = this.contributedBy
    )
}

fun List<Beer>.asDatabaseBeer(): Array<DatabaseBeer> {
    return map {
        DatabaseBeer(
            id = it.id,
            name = it.name,
            tagline = it.tagline,
            firstBrewed = it.firstBrewed ?: "",
            description = it.description,
            imageUrl = it.imageUrl ?: "",
            alcoholByVolume = it.alcoholByVolume,
            internationalBitternessUnits = it.internationalBitternessUnits ?: 0.0,
            targetOg = it.targetOg ?: 0.0,
            targetFg = it.targetFg ?: 0.0,
            europeanBeerConventionColor = it.europeanBeerConventionColor ?: 0.0,
            standardReferenceMethod = it.standardReferenceMethod ?: 0.0,
            ph = it.ph ?: 0.0,
            attenuationLevel = it.attenuationLevel ?: 0.0,
            volume = it.volume ?: Measurable(),
            boilVolume = it.boilVolume ?: Measurable(),
            brewersTip = it.brewersTip ?: "",
            contributedBy = it.contributedBy ?: ""
        )
    }.toTypedArray()
}

fun Beer.asFavouriteEntity() : FavouriteEntity {
    return FavouriteEntity(
        id = this.id,
        name = this.name,
        tagline = this.tagline,
        description = this.description,
        imageUrl = this.imageUrl,
        alcoholByVolume = this.alcoholByVolume,
        internationalBitternessUnits = this.internationalBitternessUnits,
        europeanBeerConventionColor = this.europeanBeerConventionColor
    )
}

fun FavouriteEntity.asDomainModel() : Beer {
    return Beer(
        id = this.id,
        name = this.name,
        tagline = this.tagline,
        description = this.description,
        imageUrl = this.imageUrl,
        alcoholByVolume = this.alcoholByVolume,
        internationalBitternessUnits = this.internationalBitternessUnits,
        europeanBeerConventionColor = this.europeanBeerConventionColor
    )
}