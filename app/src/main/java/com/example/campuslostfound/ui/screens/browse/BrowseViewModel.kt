package com.example.campuslostfound.ui.screens.browse

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
class BrowseViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<ItemEntity>>(emptyList())
    val items: StateFlow<List<ItemEntity>> = _items.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    
    private val _selectedType = MutableStateFlow("ALL") // "ALL", "LOST", "FOUND"
    val selectedType = _selectedType.asStateFlow()

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            if (_selectedType.value == "ALL") {
                repository.getAllItems().collect { list ->
                    _items.value = list
                }
            } else {
                repository.getItemsByType(_selectedType.value).collect { list ->
                    _items.value = list
                }
            }
        }
    }
    
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterItems()
    }
    
    fun onTypeSelected(type: String) {
        _selectedType.value = type
        filterItems()
    }
    
    private fun filterItems() {
        viewModelScope.launch {
            val query = _searchQuery.value
            if (query.isBlank()) {
                loadItems()
            } else {
                repository.searchItems(query).collect { list ->
                    val filteredList = if (_selectedType.value != "ALL") {
                        list.filter { it.type == _selectedType.value }
                    } else {
                        list
                    }
                    _items.value = filteredList
                }
            }
        }
    }
}
