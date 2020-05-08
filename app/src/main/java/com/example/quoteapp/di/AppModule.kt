package com.example.quoteapp.di

import android.app.Application
import android.content.Context
import com.example.quoteapp.App
import com.example.quoteapp.api.ApiService
import com.example.quoteapp.data.AppDatabase
import com.example.quoteapp.data.QuoteDao
import dagger.Module
import dagger.Provides
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
    fun provideApplication(app: App): Application = app

    @Provides
    @Singleton
    fun provideApplicationContext(app: App): Context = app.applicationContext

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
    fun providesApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideQuoteDao(appDatabase: AppDatabase): QuoteDao{
        return appDatabase.quoteDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application) = AppDatabase.getInstance(app)


}