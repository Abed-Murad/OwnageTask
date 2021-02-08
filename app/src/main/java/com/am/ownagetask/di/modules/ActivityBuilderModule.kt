package com.am.ownagetask.di.modules

import com.am.ownagetask.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.InternalCoroutinesApi

@Module
abstract class ActivityBuilderModule {
    @InternalCoroutinesApi
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributesInjectMainActivity(): MainActivity

}
