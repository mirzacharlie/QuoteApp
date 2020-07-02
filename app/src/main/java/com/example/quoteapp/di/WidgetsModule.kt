package com.example.quoteapp.di

import com.example.quoteapp.QuoteWidget
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class WidgetsModule {
    @ContributesAndroidInjector
    abstract fun contributesQuoteWidget(): QuoteWidget
}