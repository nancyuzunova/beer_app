package com.example.data.repository

import android.content.Context
import com.example.data.sharedPreferences.SettingsSharedPreferencesDataSource

class SettingsRepository (context: Context?, userId: String) {

    private val sharedPrefDataSource = SettingsSharedPreferencesDataSource(context, userId)

    fun saveSettings(notifications: Boolean?, fromFavourites: Boolean?, darkModeOn: Boolean?) {
        sharedPrefDataSource.write(notifications, fromFavourites, darkModeOn)
    }

    fun getNotificationsSetting(): Boolean {
        return sharedPrefDataSource.getNotifications()
    }

    fun getFavouritesSetting(): Boolean {
        return sharedPrefDataSource.getFavouritesSettings()
    }

    fun getDarkModeSetting(): Boolean {
        return sharedPrefDataSource.getDarkMode()
    }
}