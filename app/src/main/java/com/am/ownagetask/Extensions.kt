package com.am.ownagetask

import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.am.ownagetask.room.ContactEntity
import com.am.ownagetask.room.ContactsDatabase

const val TAG = "ContentResolver"
fun ContentResolver.updateRoomContacts(context: Context) {
    val cursor = this.query(
        ContactsContract.Contacts.CONTENT_URI,
        null,
        null,
        null,
        ContactsContract.Contacts.DISPLAY_NAME + " ASC "
    )
    var lastnumber: String? = "0"

    if (cursor != null && cursor.count > 0) {
        val newContactsList = arrayListOf<ContactEntity>()
        while (cursor.moveToNext()) {
            var number: String?
            val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            val name =
                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            if (cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    .toInt() > 0
            ) {
                val phoneCursor = this.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,

                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    arrayOf(id),
                    null
                )

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
        ContactsDatabase.getInstance(context).contactsDao().deleteAll()
        ContactsDatabase.getInstance(context).contactsDao().insertAll(newContactsList)
    }
    cursor?.close()
}