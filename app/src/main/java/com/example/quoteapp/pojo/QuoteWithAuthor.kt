package com.example.quoteapp.pojo

data class QuoteWithAuthor(
    val quoteId: Long? = null,
    val quoteText: String = "",
    val quoteAuthor: String = "",
    var isFavourite: Int = 0,
    var imgUri: String
)