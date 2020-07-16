package com.example.quoteapp.screens.favourite

import com.example.quoteapp.base.BaseViewModel
import com.example.quoteapp.QuoteRepository
import com.example.quoteapp.pojo.Quote
import javax.inject.Inject

class FavouriteVM @Inject constructor(private val repository: QuoteRepository) : BaseViewModel() {

    var favouriteQuoteList = repository.getFavouriteQuotes()

    fun removeFromFavourite(quote: Quote){
        repository.removeFromFavourite(quote)
    }

    fun deleteQuote(quote: Quote){
        repository.deleteQuote(quote)
    }

}