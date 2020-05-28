package com.example.quoteapp.di

import android.app.Application
import com.example.quoteapp.App
import com.example.quoteapp.QuoteRepository
import com.example.quoteapp.api.ForismaticApiService
import com.example.quoteapp.api.ImgDownloadService
import com.example.quoteapp.api.YandexApiService
import com.example.quoteapp.data.AuthorDao
import com.example.quoteapp.data.QuoteDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class QuoteRepositoryModule {

    @Provides
    @Singleton
    fun providesQuoteRepository(
        forismaticApiService: ForismaticApiService,
        yandexApiService: YandexApiService,
        imgDownloadService: ImgDownloadService,
        quoteDao: QuoteDao,
        authorDao: AuthorDao,
        context: Application
    ): QuoteRepository {
        return QuoteRepository(forismaticApiService, yandexApiService, imgDownloadService, quoteDao, authorDao, context)
    }
}