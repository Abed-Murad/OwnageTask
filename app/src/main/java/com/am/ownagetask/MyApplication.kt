package com.am.ownagetask

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.*
import com.am.ownagetask.background.ContactsSyncWorker
import com.am.ownagetask.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import java.util.concurrent.TimeUnit

class MyApplication : DaggerApplication() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(
            getString(R.string.contacts_observer_channel_id),
            getString(R.string.notification_channel_name)
        )
        scheduleDatabaseUpdateEvery15Min()
    }

    private fun scheduleDatabaseUpdateEvery15Min() {
        val updateContacts = PeriodicWorkRequestBuilder<ContactsSyncWorker>(
            PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
            TimeUnit.MILLISECONDS
        )
            .setConstraints(Constraints.Builder().setRequiresCharging(true).build())
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            getString(R.string.worker_tag),
            ExistingPeriodicWorkPolicy.REPLACE,
            updateContacts
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val channel = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
        return channelId
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}

