package com.am.ownagetask

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.provider.ContactsContract
import androidx.core.app.NotificationCompat
import com.am.ownagetask.ui.MainActivity
import kotlinx.coroutines.InternalCoroutinesApi

class ContactsService : Service() {

    private val contentObserver: ContactsObserver by lazy { ContactsObserver(null, this) }

    override fun onCreate() {
        super.onCreate()
    }

    @InternalCoroutinesApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification =
            NotificationCompat.Builder(this, getString(R.string.contacts_observer_channel_id))
                .setContentTitle(getString(R.string.notification_title))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build()

        startForeground(1010, notification)

        contentResolver.registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, contentObserver
        )

        return START_NOT_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        contentResolver.unregisterContentObserver(contentObserver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    companion object {
        private val TAG = ContactsService::class.java.simpleName
    }
}