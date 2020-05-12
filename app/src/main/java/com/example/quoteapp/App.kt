package com.example.quoteapp

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