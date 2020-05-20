package com.example.quoteapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quoteapp.ui.quotelist.QuoteListViewModel
import com.example.quoteapp.QuoteRepository
import com.example.quoteapp.ui.favourite.FavouriteViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory @Inject constructor(private val quoteRepository: QuoteRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuoteListViewModel::class.java)) {
            return QuoteListViewModel(
                quoteRepository
            ) as T
        } else if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            return FavouriteViewModel(
                quoteRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}