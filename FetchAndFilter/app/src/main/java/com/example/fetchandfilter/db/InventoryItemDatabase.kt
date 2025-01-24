package com.example.fetchandfilter.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fetchandfilter.network.ItemInfo

@Database(entities = [ItemInfo::class], version = 1)
abstract class InventoryItemDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
}