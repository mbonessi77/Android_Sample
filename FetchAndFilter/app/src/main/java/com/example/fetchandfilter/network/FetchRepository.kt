package com.example.fetchandfilter.network

import retrofit2.Response

class FetchRepository(private val service: FetchService) {
    suspend fun getItemsFromNetwork(): Response<List<ItemInfo>> {
        return service.getItemsFromServer()
    }
}