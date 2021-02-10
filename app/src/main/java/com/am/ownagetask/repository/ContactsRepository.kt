package com.am.ownagetask.repository

import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import com.am.ownagetask.GlobalValues
import com.am.ownagetask.room.ContactEntity
import com.am.ownagetask.room.ContactsDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsRepository @Inject constructor(var context: Context, var contactsDao: ContactsDao) {

    fun getContacts(): LiveData<List<ContactEntity>> {
        return contactsDao.getAll()
    }

    // Move to a background thread using Flow Api
    private fun updateRoomContacts(contactsList: List<ContactEntity>) {
        contactsDao.deleteAll()
        contactsDao.insertAll(contactsList)

    }

    // As much as you try to clean it, working with cursor is almost always messy!
    fun fetchContactsFromContactsProvider() {
        var contentResolver = context.contentResolver
        val cursor = getContactsQuery(contentResolver)
        var lastnumber: String? = "0"
        val newContactsList = arrayListOf<ContactEntity>()

        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                var number: String?
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                if (cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        .toInt() > 0
                ) {
                    val phoneCursor = getPhoneQuery(contentResolver, id)

                    while (phoneCursor != null && phoneCursor.moveToNext()) {
                        number =
                            phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        if (number != lastnumber) {
                            lastnumber = number
                            when (phoneCursor.getInt(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))) {
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> {
                                    newContactsList.add(ContactEntity(id, name, lastnumber))
                                    Log.d(TAG, "id:$id  name:$name  number:$lastnumber ")
                                }
                                ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> Log.d(
                                    TAG,
                                    "Not inserted"
                                )
                                ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> Log.d(
                                    TAG,
                                    "Not inserted"
                                )
                            }
                        }
                    }
                    phoneCursor?.close()
                }
            }

            updateRoomContacts(newContactsList)
        }
        cursor?.close()
    }

    private fun getContactsQuery(contentResolver: ContentResolver) =
        contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC "
        )

    private fun getPhoneQuery(contentResolver: ContentResolver, id: String?) =
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(id),
            null
        )

    fun syncWithTheCloud(contactsList: List<ContactEntity>) {
        TODO()
    }

    fun updatePermissionsStatus(status: String) {
        GlobalValues.permissionsStatus = status
    }

    fun updateUiStatus(status: String) {
        GlobalValues.uiStatus = status
    }

    fun getUiStatus() = GlobalValues.uiStatus

    fun getPermissionsStatus() = GlobalValues.permissionsStatus

    companion object {
        private val TAG = ContactsRepository::class.java.simpleName
    }

}

