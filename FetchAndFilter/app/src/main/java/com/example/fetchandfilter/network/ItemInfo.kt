package com.example.fetchandfilter.network

import com.google.gson.annotations.SerializedName

data class ItemInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("listId")
    val listId: Int,
    @SerializedName("name")
    val name: String?
)
