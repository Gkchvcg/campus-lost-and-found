package com.example.campuslostfound.ui.screens.details

import androidx.lifecycle.SavedStateHandle
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
class DetailsViewModel @Inject constructor(
    private val repository: ItemRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itemId: Int = savedStateHandle.get<String>("id")?.toIntOrNull() ?: 0

    private val _item = MutableStateFlow<ItemEntity?>(null)
    val item: StateFlow<ItemEntity?> = _item.asStateFlow()

    init {
        if (itemId > 0) {
            loadItem()
        }
    }

    private fun loadItem() {
        viewModelScope.launch {
            repository.getItemById(itemId).collect {
                _item.value = it
            }
        }
    }
}
