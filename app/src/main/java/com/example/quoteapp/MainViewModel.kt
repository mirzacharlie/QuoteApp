package com.example.quoteapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.quoteapp.pojo.Quote
import kotlinx.coroutines.Dispatchers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = QuoteRepository(application)

    var quoteList = repository.getAllQuotes()

    fun loadNewQuote() {
        repository.loadNewQuote()
    }

}