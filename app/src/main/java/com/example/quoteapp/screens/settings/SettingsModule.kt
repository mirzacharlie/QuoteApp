package com.example.quoteapp.screens.settings

import com.example.quoteapp.di.InjectionViewModelProvider
import com.example.quoteapp.di.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    @ViewModelInjection
    fun provideSettingsVM(
        fragment: SettingsFragment,
        viewModelProvider: InjectionViewModelProvider<SettingsVM>
    ): SettingsVM = viewModelProvider.get(fragment, SettingsVM::class)
}