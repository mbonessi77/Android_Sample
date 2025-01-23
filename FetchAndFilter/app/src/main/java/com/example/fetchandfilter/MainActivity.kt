package com.example.fetchandfilter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeViewModel()
        viewModel.getItemList()
    }

    private fun observeViewModel() {
        viewModel.listItems.observe(this) { list ->
            if (list.isNotEmpty()) {
                Toast.makeText(this, "${list.size}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}