package com.example.quoteapp

import androidx.lifecycle.ViewModel

class MainViewModel(private val repository: QuoteRepository) : ViewModel() {

    var quoteList = repository.getAllQuotes()

    fun loadNewQuotes() {
        repository.loadNewQuotes()
    }

}