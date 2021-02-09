package com.am.ownagetask.di.modules

import android.content.Context
import android.os.Handler
import com.am.ownagetask.repository.ContactsRepository
import com.am.ownagetask.room.ContactsDao
import com.am.ownagetask.room.ContactsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoom(context: Context): ContactsDatabase {
        return ContactsDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideHandler(): Handler? {
        return null
    }

    @Provides
    @Singleton
    fun provideContactsDao(contactsDatabase: ContactsDatabase): ContactsDao {
        return contactsDatabase.contactsDao()
    }

    @Provides
    @Singleton
    fun provideContactsRepository(context: Context, contactsDao: ContactsDao): ContactsRepository {
        return ContactsRepository(context, contactsDao)
    }
}
