package com.example.fetchandfilter.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FetchNetwork {
    val fetchService: FetchService by lazy {
        Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FetchService::class.java)
    }
}