package com.example.fetchandfilter.network

import com.example.fetchandfilter.db.InventoryDao
import retrofit2.Response

class FetchRepository(private val service: FetchService, private val inventoryDao: InventoryDao) {
    val itemsFromDb = inventoryDao.getFilteredItems()

    suspend fun getItemsFromNetwork(): Response<List<ItemInfo>> {
        return service.getItemsFromServer()
    }

    suspend fun insert(item: List<ItemInfo>) {
        inventoryDao.insertAll(item)
    }

    suspend fun delete() {
        inventoryDao.deleteAll()
    }
}