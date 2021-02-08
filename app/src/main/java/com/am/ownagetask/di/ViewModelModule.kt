
package com.am.ownagetask.di

import androidx.lifecycle.ViewModel
import com.am.ownagetask.repository.ContactsRepository
import com.am.ownagetask.room.ContactsDao
import com.am.ownagetask.ui.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {



    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel


}
