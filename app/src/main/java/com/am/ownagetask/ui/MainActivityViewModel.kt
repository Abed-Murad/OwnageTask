package com.am.ownagetask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.work.Operation
import com.am.ownagetask.repository.ContactsRepository
import com.am.ownagetask.room.ContactEntity
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(var contactsRepository: ContactsRepository) :
    ViewModel() {

    fun fetchContactsFromContactsProvider() {
        contactsRepository.fetchContactsFromContactsProvider()
    }

    fun getContacts(): LiveData<List<ContactEntity>> {
        return contactsRepository.getContacts()
    }

}

