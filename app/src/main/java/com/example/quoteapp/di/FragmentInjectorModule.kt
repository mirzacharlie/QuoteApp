package com.example.quoteapp.di

import com.example.quoteapp.QuoteListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentInjectorsModule {

    @ContributesAndroidInjector
    abstract fun quoteLisstFragmentInjector(): QuoteListFragment

}