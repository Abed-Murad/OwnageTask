
package com.am.ownagetask.di.modules

import androidx.lifecycle.ViewModel
import com.am.ownagetask.di.ViewModelKey
import com.am.ownagetask.ui.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel


}
