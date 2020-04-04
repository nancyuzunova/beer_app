package com.example.data.repository

import android.content.Context
import com.example.data.model.Filter
import com.example.data.sharedPreferences.FiltersSharedPreferencesDataSource

class FilterRepository(context: Context?, userId: String) {

    private var sharedPrefDataSource = FiltersSharedPreferencesDataSource(context, userId)

    fun saveFilters(filter: Filter) {
        sharedPrefDataSource.write(filter)
    }

    fun getFilters() : Filter {
        return sharedPrefDataSource.getFilterFromPref()
    }

    fun clearAll() {
        sharedPrefDataSource.clear()
    }
}