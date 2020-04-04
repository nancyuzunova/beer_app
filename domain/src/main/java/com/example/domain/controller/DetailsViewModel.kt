package com.example.domain.controller

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.data.asDomainModel
import com.example.data.database.BeerDatabase
import com.example.data.model.Beer
import com.example.data.repository.BeerRepository
import com.example.data.repository.FavouritesRepository
import com.example.data.repository.MAX_NUMBER_OF_FAVOURITE_BEERS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : BaseViewModel(application) {

    var id: Long = -1
        set(value) {
            field = value
            scope.launch {
                var beerToShow = beerRepository.getBeer(value)
                if(beerToShow == null){
                    beerToShow = favRepository.getBeerById(value)?.asDomainModel()
                }
                beer.value = beerToShow
            }
        }

    private val viewModelJob = Job()
    private val scope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = BeerDatabase.getDatabase(application)
    private val beerRepository = BeerRepository(database.beerDao, scope)
    private val favRepository = FavouritesRepository(database.favDao, userId)

    var beer: MutableLiveData<Beer?> = MutableLiveData()
    var isBeerFavourite = false
    var hasFreeSpace = true

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun deleteBeerFromFavourites(beerId: Long) {
        scope.launch {
            favRepository.deleteBeer(beerId)
        }
    }

    fun checkIfBeerIsFavourite(beerId: Long, onComplete : (Boolean) -> Unit) {
        scope.launch {
            val beer = favRepository.getBeerById(beerId)
            //if beer != null it is already favourite, but button is clicked, needs to be removed
            isBeerFavourite = beer != null
            onComplete(isBeerFavourite)
        }
    }

    fun addBeerToFavourites(id: Long) {
        scope.launch {
            favRepository.saveFavouriteBeer(id)
        }
    }

    fun checkForSpace(hasSpace : (Boolean) -> Unit){
        scope.launch {
            val number = favRepository.getNumberOfBeers()
            if(number >= MAX_NUMBER_OF_FAVOURITE_BEERS){
                hasFreeSpace = false
            }
            hasSpace(hasFreeSpace)
        }
    }
}