package com.example.quoteapp.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ImgDownloadService {

    @Streaming
    @GET
    suspend fun downloadImg(@Url url: String): Response<ResponseBody>
}