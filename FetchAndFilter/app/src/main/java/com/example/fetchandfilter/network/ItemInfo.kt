package com.example.fetchandfilter.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "inventory_table")
data class ItemInfo(
    @SerializedName("id")
    @PrimaryKey @ColumnInfo("id") val id: Int,
    @SerializedName("listId")
    @ColumnInfo("list_id") val listId: Int,
    @SerializedName("name")
    @ColumnInfo("item_name") val name: String
)
