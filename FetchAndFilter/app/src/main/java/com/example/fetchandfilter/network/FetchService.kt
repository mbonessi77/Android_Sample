package com.example.fetchandfilter.network

import retrofit2.Response
import retrofit2.http.GET

interface FetchService {
    @GET("/hiring.json")
    suspend fun getItemsFromServer(): Response<List<ItemInfo>>
}
