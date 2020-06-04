package com.example.quoteapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface YandexApiService {

    companion object {
        const val YANDEX_BASE_URL = "https://yandex.ru/"

        private const val QUERY_PARAM_TEXT = "text"
    }

    @GET("https://yandex.ru/images/search?")
    suspend fun getSearchResult(
       @Query(QUERY_PARAM_TEXT) text: String = "путин"
    ): String
}