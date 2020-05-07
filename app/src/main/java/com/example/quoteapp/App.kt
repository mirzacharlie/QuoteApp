package com.example.quoteapp

import android.app.Application
import com.example.quoteapp.di.AppComponent
import com.example.quoteapp.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .builder()
            .build()
    }
}

lateinit var component: AppComponent