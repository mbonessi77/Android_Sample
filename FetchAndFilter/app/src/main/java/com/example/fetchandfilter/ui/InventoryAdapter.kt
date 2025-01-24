package com.example.fetchandfilter.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchandfilter.R
import com.example.fetchandfilter.network.ItemInfo

class InventoryAdapter : RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {
    private var items: List<ItemInfo> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        return InventoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.inventory_item_view, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        holder.update(items[position])
    }

    fun setData(items: List<ItemInfo>) {
        this.items = items
        notifyDataSetChanged()
    }

    class InventoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun update(itemInfo: ItemInfo) {
            itemView.findViewById<TextView>(R.id.tv_id).text = "ID: ${itemInfo.id}"
            itemView.findViewById<TextView>(R.id.tv_name).text = "Name: ${itemInfo.name}"
            itemView.findViewById<TextView>(R.id.tv_list_item_id).text = "List ID: ${itemInfo.listId}"
        }
    }
}