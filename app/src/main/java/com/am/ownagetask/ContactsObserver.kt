package com.am.ownagetask

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.util.Log

class ContactsObserver(handler: Handler?, var context: Context) : ContentObserver(handler) {

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        Log.d(TAG, "onChange 1 -> selfChange : $selfChange :  uri : $uri")
        context.contentResolver.updateRoomContacts(context)
    }

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        Log.d(TAG, "onChange 2-> selfChange : $selfChange")
    }

    companion object {
        private val TAG = ContactsObserver::class.java.simpleName
    }
}

