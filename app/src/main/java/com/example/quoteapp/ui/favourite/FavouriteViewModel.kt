package com.example.quoteapp.ui.favourite

import androidx.lifecycle.ViewModel
import com.example.quoteapp.QuoteRepository
import com.example.quoteapp.pojo.Quote

class FavouriteViewModel(private val repository: QuoteRepository) : ViewModel() {

    var favouriteQuoteList = repository.getFavouriteQuotes()

    fun removeFromFavourite(quote: Quote){
        repository.removeFromFavourite(quote)
    }

}