package com.example.beerapp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.beerapp.R
import com.example.beerapp.ui.SplashActivity
import java.lang.Exception
import java.time.LocalDateTime

const val CHANNEL_ID = "notificationChannel"

class NotifyWorker (context: Context, workerParameters: WorkerParameters)
    : Worker(context, workerParameters) {

    override fun doWork(): Result {
        try {
            triggerNotification()
            return Result.success()
        } catch (e : Exception){
            return Result.failure()
        }
    }

    private fun triggerNotification() {
        val intent = Intent(applicationContext, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)
        var notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.beer)
            .setContentTitle(applicationContext.resources.getString(R.string.notification_title))
            .setContentText(applicationContext.resources.getString(R.string.notification_text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .build()

        with(NotificationManagerCompat.from(applicationContext)){
            if(getNotificationChannel(CHANNEL_ID) == null){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    val name = "Notifications"
                    val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
                    createNotificationChannel(channel)
                }
            }
            notify(createId(), notification)
        }
    }

    private fun createId(): Int {
        return System.currentTimeMillis().toInt()
    }
}