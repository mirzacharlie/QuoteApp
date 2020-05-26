package com.example.quoteapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.quoteapp.App
import com.example.quoteapp.SettingsManager
import com.example.quoteapp.SyncManager
import com.example.quoteapp.api.ForismaticApiService
import com.example.quoteapp.api.ForismaticApiService.Companion.FORISMATIC_BASE_URL
import com.example.quoteapp.api.YandexApiService
import com.example.quoteapp.api.YandexApiService.Companion.YANDEX_BASE_URL
import com.example.quoteapp.data.AppDatabase
import com.example.quoteapp.data.QuoteDao
import com.example.quoteapp.utils.APP_PREFERENCES
import com.example.quoteapp.utils.NAMED_FORISMATIC_RETROFIT
import com.example.quoteapp.utils.NAMED_YANDEX_RETROFIT
import dagger.Module
import dagger.Provides
import dagger.android.support.DaggerAppCompatActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: App): Context
            = app.applicationContext

    @Provides
    @Singleton
    @Named(NAMED_FORISMATIC_RETROFIT)
    fun providesForismaticRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(FORISMATIC_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named(NAMED_YANDEX_RETROFIT)
    fun providesYandexRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(YANDEX_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesForismaticApi(@Named(NAMED_FORISMATIC_RETROFIT)retrofit: Retrofit): ForismaticApiService =
        retrofit.create(ForismaticApiService::class.java)

    @Provides
    @Singleton
    fun providesYandexApi(@Named(NAMED_YANDEX_RETROFIT)retrofit: Retrofit): YandexApiService =
        retrofit.create(YandexApiService::class.java)

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