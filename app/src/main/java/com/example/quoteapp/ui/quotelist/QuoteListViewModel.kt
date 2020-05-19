package com.example.quoteapp.ui.quotelist

import androidx.lifecycle.ViewModel
import com.example.quoteapp.QuoteRepository
import com.example.quoteapp.pojo.Quote

class QuoteListViewModel(private val repository: QuoteRepository) : ViewModel() {

    var quoteList = repository.getAllQuotes()

    fun loadNewQuotes() {
        repository.loadNewQuotes()
    }

    fun addToFavourite(quote: Quote){
        repository.addToFavourite(quote)
    }

}