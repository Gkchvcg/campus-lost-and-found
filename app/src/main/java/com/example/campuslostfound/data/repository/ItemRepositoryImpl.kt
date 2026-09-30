package com.example.campuslostfound.data.repository

import com.example.campuslostfound.data.local.dao.ItemDao
import com.example.campuslostfound.data.local.entity.ItemEntity
import com.example.campuslostfound.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val dao: ItemDao
) : ItemRepository {

    override fun getAllItems(): Flow<List<ItemEntity>> {
        return dao.getAllItems()
    }

    override fun getItemById(id: Int): Flow<ItemEntity?> {
        return dao.getItemById(id)
    }

    override fun getItemsByType(type: String): Flow<List<ItemEntity>> {
        return dao.getItemsByType(type)
    }

    override fun searchItems(query: String): Flow<List<ItemEntity>> {
        return dao.searchItems(query)
    }

    override suspend fun insertItem(item: ItemEntity): Long {
        return dao.insertItem(item)
    }

    override suspend fun updateItem(item: ItemEntity) {
        dao.updateItem(item)
    }

    override suspend fun deleteItem(item: ItemEntity) {
        dao.deleteItem(item)
    }

    override suspend fun markAsRecovered(id: Int) {
        dao.markAsRecovered(id)
    }
}
