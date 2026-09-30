package com.example.campuslostfound.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campuslostfound.data.local.entity.ItemEntity
import com.example.campuslostfound.domain.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _myItems = MutableStateFlow<List<ItemEntity>>(emptyList())
    val myItems: StateFlow<List<ItemEntity>> = _myItems.asStateFlow()

    init {
        loadMyItems()
    }

    private fun loadMyItems() {
        viewModelScope.launch {
            // For a real app, this would filter by the current user's ID.
            // Since there's no auth, we'll just show all items for now,
            // or perhaps we could simulate it by adding a "postedByMe" flag.
            // For this portfolio app without auth, let's just show all items
            // as if the current user posted them, or filter by a dummy user.
            repository.getAllItems().collect { list ->
                _myItems.value = list
            }
        }
    }
    
    fun deleteItem(item: ItemEntity) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }
    
    fun markAsRecovered(id: Int) {
        viewModelScope.launch {
            repository.markAsRecovered(id)
        }
    }
}
