package com.example.quoteapp.screens.detail

import androidx.lifecycle.LiveData
import javax.inject.Inject
import com.example.quoteapp.BaseViewModel
import com.example.quoteapp.QuoteRepository
import com.example.quoteapp.pojo.Author

class DetailVM @Inject constructor(private val repository: QuoteRepository) : BaseViewModel() {

    var author = repository.author

    var quoteWithAuthor = repository.quoteWithAuthor

    fun setQuoteWithAuthor(id: Long){
        repository.initQuoteWithAuthor(id)
    }

    fun setAuthor(name: String) {
        repository.initializeAuthor(name)
    }

}