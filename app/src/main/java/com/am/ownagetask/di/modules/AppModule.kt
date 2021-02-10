package com.am.ownagetask.di.modules

import android.app.Application
import android.content.Context
import com.am.ownagetask.repository.ContactsRepository
import com.am.ownagetask.room.ContactsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun providesContext(app: Application): Context {
        return app.applicationContext
    }
}
