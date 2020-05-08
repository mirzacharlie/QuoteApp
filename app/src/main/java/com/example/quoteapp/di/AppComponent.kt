package com.example.quoteapp.di

import android.app.Application
import com.example.quoteapp.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        ActivityInjectorsModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

//        @BindsInstance
//        fun appModule(appModule: AppModule?): Builder

        fun build(): AppComponent
    }
    fun inject(application: Application)
}