package com.example.fetchandfilter

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

    //Launches a coroutine to fetch information from the backend
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
                _errorMessage.value = NETWORK_ERROR_MESSAGE
            }
        }
    }

    //Filters out items with bad data from the original response
    //Then sorts remaining info by listId followed by item name
    @VisibleForTesting
    internal fun processItemsResponse(list: List<ItemInfo>) {
        try {
            val result = list.filter { item -> !item.name.isNullOrEmpty() }
            _listItems.value = result.sortedWith(
                compareBy(
                    { it.listId },
                    { it.name?.removePrefix("Item ")?.toInt() }
                )
            )
        } catch (e: Exception) {
            _errorMessage.value = FILTER_ERROR_MESSAGE
        }
    }
}