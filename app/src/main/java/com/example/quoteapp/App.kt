package com.example.quoteapp

import android.app.Application
import com.example.quoteapp.di.AppComponent
import com.example.quoteapp.di.AppModule
import com.example.quoteapp.di.DaggerAppComponent

class App : BaseApplication() {

    override fun onCreate() {

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

        super.onCreate()
    }
}