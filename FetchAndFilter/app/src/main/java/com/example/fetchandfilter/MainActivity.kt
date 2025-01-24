package com.example.fetchandfilter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchandfilter.ui.InventoryAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: InventoryAdapter
    private lateinit var rv: RecyclerView

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
        observeViewModel()
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