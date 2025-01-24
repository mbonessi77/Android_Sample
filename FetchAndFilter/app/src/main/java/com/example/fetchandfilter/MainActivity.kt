package com.example.fetchandfilter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchandfilter.network.FetchNetwork
import com.example.fetchandfilter.ui.InventoryAdapter
import com.example.fetchandfilter.ui.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: InventoryAdapter
    private lateinit var rv: RecyclerView

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            FetchNetwork.fetchService,
            (application as FetchApplication).db.inventoryDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
        observeViewModel()
        viewModel.deleteStaleDb()
        viewModel.getItemList()
    }

    private fun observeViewModel() {
        viewModel.listItems.observe(this) { list ->
            if (list.isNotEmpty()) {
                adapter.setData(list)
            }
        }
    }

    private fun initUi() {
        adapter = InventoryAdapter()
        rv = findViewById(R.id.rv_items)

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
    }
}