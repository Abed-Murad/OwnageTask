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

    private val _contacts: MutableLiveData<List<ContactEntity>> = MutableLiveData()
    val contacts: LiveData<List<ContactEntity>> = _contacts


    fun updateContacts(contactsList: List<ContactEntity>) {
        contactsList
    }

    private fun getContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            _contacts.value = contactsRepository.getContacts().value
        }
    }


}