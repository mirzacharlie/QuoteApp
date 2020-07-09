package com.example.quoteapp.api

import com.example.quoteapp.pojo.Quote
import retrofit2.http.POST
import retrofit2.http.Query

interface ForismaticApiService {

    companion object {
        const val FORISMATIC_BASE_URL = "https://api.forismatic.com/api/1.0/"

        private const val QUERY_PARAM_METHOD = "method"
        private const val QUERY_PARAM_FORMAT = "format"
        private const val QUERY_PARAM_KEY = "key"
        private const val QUERY_PARAM_LANG = "ru"
    }

    @POST(".")
    suspend fun getQuote(
        @Query(QUERY_PARAM_METHOD) method: String = "getQuote",
        @Query(QUERY_PARAM_FORMAT) format: String = "json",
        @Query(QUERY_PARAM_KEY) key: String = "",
        @Query(QUERY_PARAM_LANG) lang: String = "ru"
    ): Quote

}