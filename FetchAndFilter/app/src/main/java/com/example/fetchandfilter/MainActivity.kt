package com.example.fetchandfilter

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
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
    private lateinit var loadingIndicator: ProgressBar

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(FetchNetwork.fetchService)
    }

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
                loadingIndicator.visibility = View.GONE
                adapter.setData(list)
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                loadingIndicator.visibility = View.GONE
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initUi() {
        adapter = InventoryAdapter()
        rv = findViewById(R.id.rv_items)
        loadingIndicator = findViewById(R.id.loading_indicator)

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
    }
}