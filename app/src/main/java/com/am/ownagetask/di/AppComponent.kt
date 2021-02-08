package com.am.ownagetask.di

import android.app.Application
import com.am.ownagetask.MyApplication
import com.am.ownagetask.di.modules.ActivityBuilderModule
import com.am.ownagetask.di.modules.AppModule
import com.am.ownagetask.di.modules.DatabaseModule
import com.am.ownagetask.di.modules.ViewModelFactoryModule
import com.am.ownagetask.repository.ContactsRepository
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, ActivityBuilderModule::class,
        AppModule::class, DatabaseModule::class, ViewModelFactoryModule::class]
)
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
    fun repository(): ContactsRepository
}
