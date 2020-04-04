package com.example.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.data.asDatabaseBeer
import com.example.data.asDomainModel
import com.example.data.database.BeerDao
import com.example.data.database.DatabaseBeer
import com.example.data.model.Beer
import com.example.data.model.Filter
import com.example.data.service.BeerApi
import com.example.data.service.BoundaryCallback
import com.example.data.service.MAX_NUMBER_OF_PAGES
import com.example.data.sharedPreferences.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val PAGE_ONE = 1

class BeerRepository(private val beerDao: BeerDao, scope: CoroutineScope) :
    BoundaryCallbackListener {

    var boundaryCallback = BoundaryCallback(this, scope)

    val allBeers: LiveData<PagedList<DatabaseBeer>> = let {
        val dataSource = beerDao.getAllBeers()
        val config = PagedList.Config.Builder().setPageSize(BEERS_PER_PAGE).build()
        LivePagedListBuilder(dataSource, config)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

    companion object {
        val BEERS_PER_PAGE = 25
    }

    override suspend fun loadPage(page: Int, filter: Filter) {
        val mapFilters: MutableMap<String, String?> =
            mutableMapOf("page" to page.toString(), "per_page" to BEERS_PER_PAGE.toString())
        constructFilters(filter, mapFilters)
        withContext(Dispatchers.IO) {
            val beerList = BeerApi.retrofitService.getAllBeers(mapFilters).await()
            if (beerList.size < BEERS_PER_PAGE) {
                boundaryCallback.totalPages = page
            }
            beerDao.insertAll(*beerList.asDatabaseBeer())
        }
    }

    suspend fun getBeer(id: Long): Beer? {
        return beerDao.getBeer(id)?.asDomainModel()
    }

    suspend fun getBeersFiltered(filter: Filter) {
        boundaryCallback.page = PAGE_ONE
        boundaryCallback.filter = filter
        val mapFilters : MutableMap<String, String?> =
            mutableMapOf("page" to PAGE_ONE.toString(), "per_page" to BEERS_PER_PAGE.toString())
        constructFilters(filter, mapFilters)
        withContext(Dispatchers.IO) {
            val beerListFiltered =
                BeerApi.retrofitService.getAllBeers(mapFilters).await()
            if (beerListFiltered.size < BEERS_PER_PAGE) {
                boundaryCallback.totalPages = PAGE_ONE
            } else {
                boundaryCallback.totalPages = MAX_NUMBER_OF_PAGES
            }
            beerDao.deleteAll()
            beerDao.insertAll(*beerListFiltered.asDatabaseBeer())
        }
    }

    suspend fun getRandomBeer(): Beer {
        val random = BeerApi.retrofitService.getRandomBeer().await()
        withContext(Dispatchers.IO){
            beerDao.insertAll(*random.asDatabaseBeer())
        }
        return random[0]
    }

    private fun constructFilters(filter: Filter, mapFilters: MutableMap<String, String?>) {
        if (filter.name != null && filter.name!!.isNotEmpty()) {
            mapFilters[NAME] = filter.name
        }
        if (filter.yeast != null && filter.yeast.isNotEmpty()) {
            mapFilters[YEAST] = filter.yeast
        }
        if (filter.hops != null && filter.hops.isNotEmpty()) {
            mapFilters[HOPS] = filter.hops
        }
        if (filter.malt != null && filter.malt.isNotEmpty()) {
            mapFilters[MALT] = filter.malt
        }
        if (filter.food != null && filter.food.isNotEmpty()) {
            mapFilters[FOOD] = filter.food
        }
        if (filter.ibuFrom != null && filter.ibuFrom != 0.0) {
            mapFilters[IBU_FROM] = filter.ibuFrom.toString()
        }
        if (filter.ibuTo != null && filter.ibuTo != 0.0) {
            mapFilters[IBU_TO] = filter.ibuTo.toString()
        }
        if (filter.abvFrom != null && filter.abvFrom != 0.0) {
            mapFilters[ABV_FROM] = filter.abvFrom.toString()
        }
        if (filter.abvTo != null && filter.abvTo != 0.0) {
            mapFilters[ABV_TO] = filter.abvTo.toString()
        }
        if (filter.ebcFrom != null && filter.ebcFrom != 0.0) {
            mapFilters[EBC_FROM] = filter.ebcFrom.toString()
        }
        if (filter.ebcTo != null && filter.ebcTo != 0.0) {
            mapFilters[EBC_TO] = filter.ebcTo.toString()
        }
        if (filter.after != null && filter.after.isNotEmpty()) {
            mapFilters[BREWED_AFTER] = filter.after
        }
        if (filter.before != null && filter.before.isNotEmpty()) {
            mapFilters[BREWED_BEFORE] = filter.before
        }
    }
}

interface BoundaryCallbackListener {
    suspend fun loadPage(page: Int, filter: Filter)
}