package com.example.beerapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.beerapp.R
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashActivity: AppCompatActivity(){

    private val SPLASH_TIME_OUT:Long = 3000 // 3 sec

    private var activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        activityScope.launch {
            delay(SPLASH_TIME_OUT)
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
