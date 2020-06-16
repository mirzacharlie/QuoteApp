package com.example.quoteapp.screens.detail

import android.widget.Toast
import androidx.lifecycle.LiveData
import javax.inject.Inject
import com.example.quoteapp.BaseViewModel
import com.example.quoteapp.QuoteRepository
import com.example.quoteapp.pojo.Author
import com.example.quoteapp.pojo.Quote

class DetailVM @Inject constructor(private val repository: QuoteRepository) : BaseViewModel() {

    var quoteWithAuthor = repository.quoteWithAuthor

    fun initQuoteWithAuthor(id: Long){
        repository.initQuoteWithAuthor(id)
    }

    fun changeFavourite(id: Long, isFav: Int){
        if (isFav == 0){
            repository.updateFavouriteByID(id, 1)
        } else {
            repository.updateFavouriteByID(id, 0)
        }
    }

}