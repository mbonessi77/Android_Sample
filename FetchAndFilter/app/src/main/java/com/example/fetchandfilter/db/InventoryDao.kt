package com.example.fetchandfilter.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fetchandfilter.network.ItemInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventory_table WHERE item_name IS NOT NULL AND item_name != '' ORDER BY list_id ASC, item_name ASC")
    fun getFilteredItems(): Flow<List<ItemInfo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(itemList: List<ItemInfo>)

    @Query("DELETE FROM inventory_table")
    suspend fun deleteAll()
}