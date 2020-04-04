package com.example.data.sharedPreferences

import android.content.Context

const val NOTIFICATIONS = "notifications"
const val FROM_FAVOURITES = "randomFromFavourites"
const val DARK_MODE = "darkMode"

class SettingsSharedPreferencesDataSource (context: Context?, private val userId: String) {

    private val name = "userSharedPref_" + userId
    private val sharedPref = context?.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun write(notifications: Boolean?, inEnglish: Boolean?, darkModeOn: Boolean?) {
        val editor = sharedPref?.edit()
        if (notifications != null) {
            editor?.putBoolean(NOTIFICATIONS, notifications)
        }
        if (inEnglish != null) {
            editor?.putBoolean(FROM_FAVOURITES, inEnglish)
        }
        if (darkModeOn != null) {
            editor?.putBoolean(DARK_MODE, darkModeOn)
        }
        editor?.apply()
    }

    fun getNotifications(): Boolean {
        return sharedPref?.getBoolean(NOTIFICATIONS, false) ?: false
    }

    fun getFavouritesSettings(): Boolean {
        return sharedPref?.getBoolean(FROM_FAVOURITES, false) ?: false
    }

    fun getDarkMode(): Boolean {
        return sharedPref?.getBoolean(DARK_MODE, false) ?: false
    }
}