package com.example.quoteapp.api

import com.example.quoteapp.pojo.Quote
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val QUERY_PARAM_METHOD = "method"
        private const val QUERY_PARAM_FORMAT = "format"
        private const val QUERY_PARAM_KEY = "key"
        private const val QUERY_PARAM_LANG = "ru"
    }

    @POST(".")
    suspend fun loadNewQuoteFromInternet(
        @Query(QUERY_PARAM_METHOD) method: String = "getQuote",
        @Query(QUERY_PARAM_FORMAT) format: String = "json",
        @Query(QUERY_PARAM_KEY) key: String = "",
        @Query(QUERY_PARAM_LANG) lang: String = "ru"
    ): Quote
}