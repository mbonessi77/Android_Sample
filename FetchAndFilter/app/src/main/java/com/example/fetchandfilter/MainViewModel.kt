package com.example.fetchandfilter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.fetchandfilter.network.FetchRepository
import com.example.fetchandfilter.network.FetchNetwork
import com.example.fetchandfilter.network.ItemInfo
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MainViewModel(private val fetchRepository: FetchRepository) : ViewModel() {
    val listItems: LiveData<List<ItemInfo>> = fetchRepository.itemsFromDb.asLiveData()

    fun getItemList() {
        viewModelScope.launch {
            val result = fetchRepository.getItemsFromNetwork()
            if (result.isSuccessful) {
                saveItemsLocally(result.body()!!)
            }
        }
    }

    fun deleteStaleDb() {
        viewModelScope.launch {
            fetchRepository.delete()
        }
    }

    private fun saveItemsLocally(items: List<ItemInfo>) {
        viewModelScope.launch {
            fetchRepository.insert(items)
        }
    }
}