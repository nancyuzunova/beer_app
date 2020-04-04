package com.example.domain.controller

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.data.sharedPreferences.DARK_MODE
import com.example.data.sharedPreferences.NOTIFICATIONS
import com.google.android.gms.auth.api.signin.GoogleSignIn

open class BaseViewModel(application: Application) : AndroidViewModel(application){

    val user = GoogleSignIn.getLastSignedInAccount(application)
    val userId = user?.id ?: ""

    private val sharedPrefName = "userSharedPref_" + userId

    fun isDarkModeSet(application: Application): Boolean {
        val sharedPref = application.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(DARK_MODE, false)
    }

    fun areNotificationsOn(application: Application): Boolean {
        val sharedPref = application.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(NOTIFICATIONS, false)
    }
}