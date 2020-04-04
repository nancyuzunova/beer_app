package com.example.data.repository

import androidx.lifecycle.LiveData
import com.example.data.asDomainModel
import com.example.data.asFavouriteEntity
import com.example.data.database.FavouriteEntity
import com.example.data.database.FavouritesDao
import com.example.data.model.Beer
import com.example.data.service.BeerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val MAX_NUMBER_OF_FAVOURITE_BEERS = 15

class FavouritesRepository(private val favouriteDao: FavouritesDao, private val userId : String) {

    val favouriteBeers: LiveData<List<FavouriteEntity>> = favouriteDao.getAllFavouriteBeers(userId)

    suspend fun getNumberOfBeers() : Int = favouriteDao.getNumberOfFavouriteBeers(userId)

    suspend fun saveFavouriteBeer(beerId: Long) {
        withContext(Dispatchers.IO) {
            if(getNumberOfBeers() < MAX_NUMBER_OF_FAVOURITE_BEERS) {
                val beer = BeerApi.retrofitService.getBeerById(beerId).await()[0]
                val favBeer = beer.asFavouriteEntity()
                favBeer.userId = userId
                favouriteDao.insertBeer(favBeer)
            }
        }
    }

    suspend fun deleteBeer(beerId: Long) {
        withContext(Dispatchers.IO) {
            favouriteDao.deleteBeer(beerId, userId)
        }
    }

    suspend fun getBeerById(beerId: Long): FavouriteEntity? {
        return favouriteDao.getBeerById(beerId, userId)
    }

    suspend fun getRandomBeer(): Beer? {
        return favouriteDao.getRandomBeer(userId)?.asDomainModel()
    }
}
