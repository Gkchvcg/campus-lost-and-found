package com.example.campuslostfound.domain.repository

import com.example.campuslostfound.data.local.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getAllItems(): Flow<List<ItemEntity>>
    fun getItemById(id: Int): Flow<ItemEntity?>
    fun getItemsByType(type: String): Flow<List<ItemEntity>>
    fun searchItems(query: String): Flow<List<ItemEntity>>
    suspend fun insertItem(item: ItemEntity): Long
    suspend fun updateItem(item: ItemEntity)
    suspend fun deleteItem(item: ItemEntity)
    suspend fun markAsRecovered(id: Int)
}
