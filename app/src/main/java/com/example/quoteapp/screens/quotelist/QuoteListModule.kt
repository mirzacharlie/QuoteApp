package com.example.quoteapp.screens.quotelist

import com.example.quoteapp.di.InjectionViewModelProvider
import com.example.quoteapp.di.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class QuoteListModule {

    @Provides
    @ViewModelInjection
    fun provideQuoteListVM(
        fragment: QuoteListFragment,
        viewModelProvider: InjectionViewModelProvider<QuoteListVM>
    ): QuoteListVM = viewModelProvider.get(fragment, QuoteListVM::class)
}