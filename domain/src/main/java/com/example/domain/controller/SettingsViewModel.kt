package com.example.domain.controller

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.example.data.repository.SettingsRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class SettingsViewModel (application: Application) : BaseViewModel(application) {

    private val settingsRepo = SettingsRepository(application, userId)

    val notifications: Boolean = settingsRepo.getNotificationsSetting()
    val fromFavourites: Boolean = settingsRepo.getFavouritesSetting()
    val darkMode: Boolean = settingsRepo.getDarkModeSetting()

    fun getUserInfo() = user

    fun saveSettings(notifications: Boolean?, fromFavourites: Boolean?, darkModeOn: Boolean?) {
        settingsRepo.saveSettings(notifications, fromFavourites, darkModeOn)
    }

    fun signOut(context: Context?, onCompleteListener: () -> Unit) {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()
        val client = GoogleSignIn.getClient(context!!, googleSignInOptions)
        client.signOut().addOnCompleteListener {
            try {
                it.getResult(ApiException::class.java)
                onCompleteListener()
            } catch (e : ApiException){
                Log.w("SettingsFragment", "signOutResult: failed code = " + e.statusCode)
            }
        }
    }
}