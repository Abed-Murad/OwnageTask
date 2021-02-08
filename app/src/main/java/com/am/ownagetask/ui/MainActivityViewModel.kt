package com.am.ownagetask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.ownagetask.repository.ContactsRepository
import com.am.ownagetask.room.ContactEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(var contactsRepository: ContactsRepository) :
    ViewModel() {


    fun updateContacts(contactsList: List<ContactEntity>) {
        contactsRepository.updateContacts(contactsList)
    }

     fun getContacts(): LiveData<List<ContactEntity>> {
        return contactsRepository.getContacts()
    }


}