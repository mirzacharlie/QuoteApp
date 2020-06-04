package com.example.quoteapp.screens.quotelist

import android.app.Application
import android.content.Context
import androidx.work.WorkManager
import com.example.quoteapp.BaseViewModel
import com.example.quoteapp.QuoteRepository
import com.example.quoteapp.pojo.Quote
import com.example.quoteapp.workers.DownloadWorker
import javax.inject.Inject

class QuoteListVM @Inject constructor(private val repository: QuoteRepository, val app: Application) : BaseViewModel() {

    var quoteList = repository.getAllQuotes()

    var status = WorkManager.getInstance(app)
        .getWorkInfosForUniqueWorkLiveData(DownloadWorker.TAG)

    fun loadNewQuotes() {
        repository.loadNewQuotes()
    }

    fun addToFavourite(quote: Quote){
        repository.addToFavourite(quote)
    }

}