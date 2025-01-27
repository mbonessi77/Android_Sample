package com.example.fetchandfilter

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchandfilter.network.FetchRepository
import com.example.fetchandfilter.network.ItemInfo
import kotlinx.coroutines.launch

class MainViewModel(private val fetchRepository: FetchRepository) : ViewModel() {
    private val _listItems = MutableLiveData<List<ItemInfo>>()
    val listItems: LiveData<List<ItemInfo>> = _listItems

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getItemList() {
        viewModelScope.launch {
            try {
                val result = fetchRepository.getItemsFromNetwork()
                if (result.isSuccessful) {
                    result.body()?.let {
                        processItemsResponse(it)
                    }
                } else {
                    _errorMessage.value = result.message()
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error creating local database"
                Log.d("TAG", "deleteStaleDb: ${e.message}")
            }
        }
    }

    @VisibleForTesting
    internal fun processItemsResponse(list: List<ItemInfo>) {
        val result = list.filter { item -> !item.name.isNullOrEmpty() }
        _listItems.value = result.sortedWith(
            compareBy(
                { it.listId },
                { it.name?.removePrefix("Item ")?.toInt() }
            )
        )
    }
}