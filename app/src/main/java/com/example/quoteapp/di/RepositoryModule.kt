package com.example.quoteapp.di

import com.example.quoteapp.QuoteRepository
import com.example.quoteapp.api.ApiService
import com.example.quoteapp.data.QuoteDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesUserRepository(apiService: ApiService, quoteDao: QuoteDao): QuoteRepository {
        return QuoteRepository(apiService, quoteDao)
    }
}