package com.example.beerapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.work.WorkManager
import com.example.beerapp.R
import com.example.beerapp.notifications.notificationWork
import com.example.domain.controller.BaseViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel : BaseViewModel by lazy {
        ViewModelProvider(this).get(BaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(viewModel.isDarkModeSet(application)){
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.LightTheme)
        }
        setContentView(R.layout.activity_main)
        val navController = this.findNavController(R.id.mainNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.mainNavHostFragment)
        return navController.navigateUp()
    }

    override fun onStop() {
        if(viewModel.areNotificationsOn(application)){
            WorkManager.getInstance(this).enqueue(notificationWork)
        }
        super.onStop()
    }
}
