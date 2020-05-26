package com.example.quoteapp.di

import com.example.quoteapp.QuoteRepository
import com.example.quoteapp.api.ForismaticApiService
import com.example.quoteapp.api.YandexApiService
import com.example.quoteapp.data.QuoteDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class QuoteRepositoryModule {

    @Provides
    @Singleton
    fun providesQuoteRepository(forismaticApiService: ForismaticApiService, yandexApiService: YandexApiService, quoteDao: QuoteDao): QuoteRepository {
        return QuoteRepository(forismaticApiService,  yandexApiService, quoteDao)
    }
}