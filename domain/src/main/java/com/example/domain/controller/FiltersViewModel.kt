package com.example.domain.controller

import android.app.Application
import com.example.data.model.Filter
import com.example.data.repository.FilterRepository

class FiltersViewModel(application: Application) : BaseViewModel(application){

    private val filterRepository = FilterRepository(application, userId)

    fun getFilter() : Filter{
        return filterRepository.getFilters()
    }

    fun saveFilters(filter: Filter) {
        filterRepository.saveFilters(filter)
    }

    fun clearFilters() {
        filterRepository.clearAll()
    }
}