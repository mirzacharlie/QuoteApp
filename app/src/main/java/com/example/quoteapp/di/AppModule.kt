package com.example.quoteapp.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.quoteapp.App
import com.example.quoteapp.SettingsManager
import com.example.quoteapp.SyncManager
import com.example.quoteapp.api.ApiService
import com.example.quoteapp.data.AppDatabase
import com.example.quoteapp.data.QuoteDao
import com.example.quoteapp.utils.APP_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.android.support.DaggerAppCompatActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule {

    companion object{
        private const val BASE_URL = "https://api.forismatic.com/api/1.0/"
    }

    @Provides
    @Singleton
    fun provideApplicationContext(app: App): Context
            = app.applicationContext

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideQuoteDao(appDatabase: AppDatabase): QuoteDao =
        appDatabase.quoteDao()

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase
            = AppDatabase.getInstance(app)

    @Provides
    @Singleton
    fun provideSettingsManager(sharedPreferences: SharedPreferences): SettingsManager{
        return SettingsManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences =
        app.getSharedPreferences(APP_PREFERENCES, DaggerAppCompatActivity.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSyncManager(settingsManager: SettingsManager, app: Application): SyncManager =
        SyncManager(settingsManager, app)
}