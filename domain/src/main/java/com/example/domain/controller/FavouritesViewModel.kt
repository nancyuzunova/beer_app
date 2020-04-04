package com.example.domain.controller

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.data.database.BeerDatabase
import com.example.data.repository.FavouritesRepository

class FavouritesViewModel(application: Application) : BaseViewModel(application){

    private val database = BeerDatabase.getDatabase(application)
    private val favRepository = FavouritesRepository(database.favDao, userId)

    val favBeers
        get() = favRepository.favouriteBeers

    private val _navigateToBeerDetails = MutableLiveData<Long>()
    val navigateToBeerDetails
        get() = _navigateToBeerDetails

    fun onBeerClicked(beerId: Long) {
        _navigateToBeerDetails.value = beerId
    }

    fun onBeerDetailsNavigated(){
        _navigateToBeerDetails.value = null
    }
}