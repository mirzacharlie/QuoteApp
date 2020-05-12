package com.example.quoteapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.quoteapp.api.ApiService
import com.example.quoteapp.pojo.Quote
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repository: QuoteRepository) : ViewModel() {

    var quoteList = repository.getAllQuotes()

    fun loadNewQuote() {
        repository.loadNewQuote()
    }

}