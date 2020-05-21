package com.example.quoteapp.screens.favourite

import com.example.quoteapp.di.InjectionViewModelProvider
import com.example.quoteapp.di.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class FavouriteModule {

    @Provides
    @ViewModelInjection
    fun provideFavouriteVM(
        fragment: FavouriteFragment,
        viewModelProvider: InjectionViewModelProvider<FavouriteVM>
    ): FavouriteVM = viewModelProvider.get(fragment, FavouriteVM::class)
}