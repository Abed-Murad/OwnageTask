package com.am.ownagetask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
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


    fun updatePermissionsStatus(status: String) = contactsRepository.updatePermissionsStatus(status)
    fun getPermissionsStatus() = contactsRepository.getPermissionsStatus()

    fun updateUIStatus(status: String) = contactsRepository.updateUiStatus(status)

    fun getUIStatus() = contactsRepository.getUiStatus()


}

