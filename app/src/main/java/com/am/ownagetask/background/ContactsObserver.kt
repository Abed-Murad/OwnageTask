package com.am.ownagetask.background

import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.util.Log
import com.am.ownagetask.repository.ContactsRepository
import javax.inject.Inject

class ContactsObserver @Inject constructor(
    handler: Handler?,
    var contactsRepository: ContactsRepository
) : ContentObserver(handler) {

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        Log.d(TAG, "onChange 1 -> selfChange : $selfChange :  uri : $uri")
        contactsRepository.fetchContactsFromContactsProvider()
    }

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        Log.d(TAG, "onChange 2-> selfChange : $selfChange")
    }

    companion object {
        private val TAG = ContactsObserver::class.java.simpleName
    }
}

