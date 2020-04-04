package com.example.data.sharedPreferences

import android.content.Context
import com.example.data.model.Filter

const val NAME = "beer_name"
const val YEAST = "yeast"
const val HOPS = "hops"
const val MALT = "malt"
const val FOOD = "food"
const val IBU_FROM = "ibu_gt"
const val IBU_TO = "ibu_lt"
const val ABV_FROM = "abv_gt"
const val ABV_TO = "abv_lt"
const val EBC_FROM = "ebc_gt"
const val EBC_TO = "ebc_lt"
const val BREWED_AFTER = "brewed_after"
const val BREWED_BEFORE = "brewed_before"

class FiltersSharedPreferencesDataSource(context: Context?, private val userId: String) {

    private val name = "sharedPref_" + userId
    private var sharedPref = context?.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun write(filter: Filter) {
        val editor = sharedPref?.edit()
        editor?.putString(NAME, filter.name)
        editor?.putString(YEAST, filter.yeast)
        editor?.putString(HOPS, filter.hops)
        editor?.putString(MALT, filter.malt)
        editor?.putString(FOOD, filter.food)
        editor?.putFloat(IBU_FROM, filter.ibuFrom?.toFloat() ?: 0F)
        editor?.putFloat(IBU_TO, filter.ibuTo?.toFloat() ?: 0F)
        editor?.putFloat(ABV_FROM, filter.abvFrom?.toFloat() ?: 0F)
        editor?.putFloat(ABV_TO, filter.abvTo?.toFloat() ?: 0F)
        editor?.putFloat(EBC_FROM, filter.ebcFrom?.toFloat() ?: 0F)
        editor?.putFloat(EBC_TO, filter.ebcTo?.toFloat() ?: 0F)
        editor?.putString(BREWED_AFTER, filter.after)
        editor?.putString(BREWED_BEFORE, filter.before)
        editor?.apply()
    }

    fun getFilterFromPref(): Filter {
        return Filter(
            name = sharedPref?.getString(NAME, "") ?: "",
            yeast = sharedPref?.getString(YEAST, "") ?: "",
            hops = sharedPref?.getString(HOPS, "") ?: "",
            malt = sharedPref?.getString(MALT, "") ?: "",
            food = sharedPref?.getString(FOOD, "") ?: "",
            ibuFrom = sharedPref?.getFloat(IBU_FROM, 0F)?.toDouble() ?: 0.0,
            ibuTo = sharedPref?.getFloat(IBU_TO, 0F)?.toDouble() ?: 0.0,
            abvFrom = sharedPref?.getFloat(ABV_FROM, 0f)?.toDouble() ?: 0.0,
            abvTo = sharedPref?.getFloat(ABV_TO, 0f)?.toDouble() ?: 0.0,
            ebcFrom = sharedPref?.getFloat(EBC_FROM, 0f)?.toDouble() ?: 0.0,
            ebcTo = sharedPref?.getFloat(EBC_TO, 0f)?.toDouble() ?: 0.0,
            after = sharedPref?.getString(BREWED_AFTER, "")?: "",
            before = sharedPref?.getString(BREWED_BEFORE, "") ?: ""
        )
    }

    fun clear() {
        val editor = sharedPref?.edit()
        editor?.clear()
        editor?.apply()
    }
}