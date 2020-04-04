package com.example.domain.controller

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.data.database.BeerDatabase
import com.example.data.model.Beer
import com.example.data.repository.BeerRepository
import com.example.data.repository.FavouritesRepository
import com.example.data.repository.FilterRepository
import com.example.data.repository.SettingsRepository
import kotlinx.coroutines.*

class HomeViewModel(application: Application) : BaseViewModel(application){

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = BeerDatabase.getDatabase(application)
    private val beerRepository = BeerRepository(database.beerDao, viewModelScope)
    private val filterRepository = FilterRepository(application, userId)
    private val settingsRepository = SettingsRepository(application, userId)
    private val favouritesRepository = FavouritesRepository(database.favDao, userId)

    val allBeers
        get() = beerRepository.allBeers

    private val _navigateToBeerDetails = MutableLiveData<Long>()
    val navigateToBeerDetails
        get() = _navigateToBeerDetails

    var nameFilter: String = ""

    companion object {
        private val FIRST_PAGE = 1
    }

    init {
        viewModelScope.launch {
            beerRepository.loadPage(FIRST_PAGE, filterRepository.getFilters())
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onBeerClicked(id: Long){
        _navigateToBeerDetails.value = id
    }

    //navigation is done, so set to null
    fun onBeerDetailsNavigated(){
        _navigateToBeerDetails.value = null
    }

    suspend fun filterBeers(name: String) {
        this.nameFilter = name
        val filter = filterRepository.getFilters()
        filter.name = name
        beerRepository.getBeersFiltered(filter)
    }

    suspend fun getRandomBeer(): Beer {
        return beerRepository.getRandomBeer()
    }

    suspend fun hasFavourites() : Boolean {
        return favouritesRepository.getNumberOfBeers() > 0
    }

    suspend fun getRandomFromFavourites(): Beer? {
        return favouritesRepository.getRandomBeer() ?: null
    }

    fun isRandomFromFavourites(): Boolean {
        return settingsRepository.getFavouritesSetting()
    }
}