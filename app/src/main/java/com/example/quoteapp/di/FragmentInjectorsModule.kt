package com.example.quoteapp.di

import com.example.quoteapp.screens.detail.DetailFragment
import com.example.quoteapp.screens.detail.DetailModule
import com.example.quoteapp.screens.settings.SettingsFragment
import com.example.quoteapp.screens.favourite.FavouriteFragment
import com.example.quoteapp.screens.favourite.FavouriteModule
import com.example.quoteapp.screens.quotelist.QuoteListFragment
import com.example.quoteapp.screens.quotelist.QuoteListModule
import com.example.quoteapp.screens.settings.SettingsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentInjectorsModule {

    @ContributesAndroidInjector(modules = [QuoteListModule::class])
    abstract fun quoteListFragmentInjector(): QuoteListFragment

    @ContributesAndroidInjector(modules = [FavouriteModule::class])
    abstract fun favouriteFragmentInjector(): FavouriteFragment

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun settingsFragmentInjector(): SettingsFragment

    @ContributesAndroidInjector(modules = [DetailModule::class])
    abstract fun detailFragmentInjector(): DetailFragment
}