package com.example.quoteapp.screens.quotelist

import com.example.quoteapp.BaseViewModel
import com.example.quoteapp.QuoteRepository
import com.example.quoteapp.pojo.Quote
import javax.inject.Inject

class QuoteListVM @Inject constructor(private val repository: QuoteRepository) : BaseViewModel() {

    var quoteList = repository.getAllQuotes()

    fun loadNewQuotes() {
        repository.loadNewQuotes()
    }

    fun addToFavourite(quote: Quote){
        repository.addToFavourite(quote)
    }

}