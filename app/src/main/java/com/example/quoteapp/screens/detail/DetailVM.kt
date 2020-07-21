package com.example.quoteapp.screens.detail

import com.example.quoteapp.base.BaseViewModel
import com.example.quoteapp.QuoteRepository
import javax.inject.Inject

class DetailVM @Inject constructor(private val repository: QuoteRepository) : BaseViewModel() {

    var quoteWithAuthor = repository.quoteWithAuthor

    fun initQuoteWithAuthor(id: Long){
        repository.initQuoteWithAuthor(id)
    }

    fun changeFavourite(id: Long, isFav: Int){
        if (isFav == 0){
            repository.updateFavouriteByIdWithBlocking(id, 1)
        } else {
            repository.updateFavouriteByIdWithBlocking(id, 0)
        }
    }

}