package com.example.quoteapp.di

import com.example.quoteapp.SettingsFragment
import com.example.quoteapp.ui.favourite.FavouriteFragment
import com.example.quoteapp.ui.quotelist.QuoteListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentInjectorsModule {

    @ContributesAndroidInjector
    abstract fun quoteListFragmentInjector(): QuoteListFragment

    @ContributesAndroidInjector
    abstract fun favouriteFragmentInjector(): FavouriteFragment

    @ContributesAndroidInjector
    abstract fun settingsFragmentInjector(): SettingsFragment

}