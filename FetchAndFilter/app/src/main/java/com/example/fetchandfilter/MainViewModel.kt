package com.example.fetchandfilter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.fetchandfilter.network.FetchRepository
import com.example.fetchandfilter.network.FetchNetwork
import com.example.fetchandfilter.network.ItemInfo
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MainViewModel(private val fetchRepository: FetchRepository) : ViewModel() {
    private val _listItems = MutableLiveData<List<ItemInfo>>()
    val listItems: LiveData<List<ItemInfo>> = _listItems

    fun getItemList() {
       viewModelScope.launch {
           val result = fetchRepository.getItemsFromNetwork()
           if (result.isSuccessful) {
               _listItems.value = result.body()
           }
       }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return MainViewModel(FetchRepository(FetchNetwork.fetchService)) as T
            }
        }
    }
}