package com.am.ownagetask.background

import android.app.*
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.provider.ContactsContract
import androidx.core.app.NotificationCompat
import com.am.ownagetask.R
import com.am.ownagetask.ui.MainActivity
import dagger.android.DaggerService
import javax.inject.Inject

class ContactsService : DaggerService() {

    @Inject
    lateinit var mContentObserver: ContactsObserver
    private val mBinder: IBinder = MyBinder()

    override fun onBind(intent: Intent?): IBinder {
        // Show Notification,
        startNotification()

        registerContactsContentObserver()
        return mBinder
    }

    private fun startNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification =
            NotificationCompat.Builder(this, getString(R.string.contacts_observer_channel_id))
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_content_text))
                .setSmallIcon(R.drawable.ic_service)
                .setContentIntent(pendingIntent)
                .build()

        startForeground(1010, notification)
    }

    private fun registerContactsContentObserver() {
        contentResolver.registerContentObserver(
            ContactsContract.Contacts.CONTENT_VCARD_URI, false, mContentObserver
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterContactsContentObserver()
    }

    private fun unregisterContactsContentObserver() {
        contentResolver.unregisterContentObserver(mContentObserver)
    }

    inner class MyBinder : Binder() {
        // Return this instance of MyService so clients can call public methods
        val service: ContactsService
            get() =// Return this instance of MyService so clients can call public methods
                this@ContactsService
    }

    companion object {
        private val TAG = ContactsService::class.java.simpleName
    }
}