package com.example.quoteapp.base

import android.annotation.SuppressLint
import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.quoteapp.workers.AppWorkerFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

@SuppressLint("Registered")
open class BaseApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var workerFactory: AppWorkerFactory

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workerFactory).build())
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}