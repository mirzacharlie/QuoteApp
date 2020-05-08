package com.example.quoteapp.di

import com.example.quoteapp.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectorsModule {
    @ContributesAndroidInjector
    abstract fun mainActivityInjector(): MainActivity
}