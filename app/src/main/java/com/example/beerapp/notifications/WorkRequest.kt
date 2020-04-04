package com.example.beerapp.notifications

import androidx.work.OneTimeWorkRequest
import java.util.concurrent.TimeUnit

const val WORK_TAG = "notificationWork" //if needed we will be able to close all work of this type

val notificationWork = OneTimeWorkRequest.Builder(NotifyWorker::class.java)
    .setInitialDelay(30, TimeUnit.MINUTES)
    .addTag(WORK_TAG)
    .build()