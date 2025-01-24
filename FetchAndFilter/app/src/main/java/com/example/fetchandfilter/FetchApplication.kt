package com.example.fetchandfilter

import android.app.Application
import androidx.room.Room
import com.example.fetchandfilter.db.InventoryItemDatabase

class FetchApplication : Application() {
    lateinit var db: InventoryItemDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
                this,
        InventoryItemDatabase::class.java,
        "item_database"
        ).build()
    }
}