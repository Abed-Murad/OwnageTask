package com.am.ownagetask.repository

import androidx.lifecycle.LiveData
import com.am.ownagetask.room.ContactEntity
import com.am.ownagetask.room.ContactsDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsRepository @Inject constructor(var contactsDao: ContactsDao) {
    fun getContacts(): LiveData<List<ContactEntity>> {
        return contactsDao.getAll()
    }

    fun updateContacts(contactsList: List<ContactEntity>) {
        contactsDao.deleteAll()
        contactsDao.insertAll(contactsList)
    }
}