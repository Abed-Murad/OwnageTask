package com.am.ownagetask.di

import android.content.Context
import com.am.ownagetask.repository.ContactsRepository
import com.am.ownagetask.room.ContactsDao
import com.am.ownagetask.room.ContactsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule() {

    @Provides
    @Singleton
    fun provideRoom(context: Context): ContactsDatabase {
        return ContactsDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideContactsDao(contactsDatabase: ContactsDatabase): ContactsDao {
        return contactsDatabase.contactsDao()
    }

    @Provides
    @Singleton
    fun provideContactsRepository(contactsDao: ContactsDao): ContactsRepository {
        return ContactsRepository(contactsDao)
    }
}
