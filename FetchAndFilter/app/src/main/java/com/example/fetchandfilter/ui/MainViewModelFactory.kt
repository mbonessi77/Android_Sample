package com.example.fetchandfilter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fetchandfilter.MainViewModel
import com.example.fetchandfilter.network.FetchRepository
import com.example.fetchandfilter.network.FetchService

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val service: FetchService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(FetchRepository(service)) as T
    }
}