package com.example.quoteapp.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ImgDownloadService {

    @GET
    fun downloadImg(@Url url: String): Response<ResponseBody>
}