package com.example.quoteapp.screens.detail

import dagger.Module
import dagger.Provides
import com.example.quoteapp.di.ViewModelInjection
import com.example.quoteapp.di.InjectionViewModelProvider

@Module
class DetailModule {

    @Provides
    @ViewModelInjection
    fun provideDetailVM(
        fragment: DetailFragment,
        viewModelProvider: InjectionViewModelProvider<DetailVM>
    ): DetailVM = viewModelProvider.get(fragment, DetailVM::class)
}