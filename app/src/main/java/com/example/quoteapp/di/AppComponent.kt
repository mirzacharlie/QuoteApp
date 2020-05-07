package com.example.quoteapp.di

import com.example.quoteapp.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)
}