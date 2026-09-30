package com.example.campuslostfound.data.repository

import com.example.campuslostfound.data.local.dao.ItemDao
import com.example.campuslostfound.data.local.entity.ItemEntity
import com.example.campuslostfound.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
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

    override suspend fun insertItem(item: ItemEntity): Long = withContext(Dispatchers.IO) {
        dao.insertItem(item)
    }

    override suspend fun updateItem(item: ItemEntity) {
        withContext(Dispatchers.IO) {
            dao.updateItem(item)
        }
    }

    override suspend fun deleteItem(item: ItemEntity) {
        withContext(Dispatchers.IO) {
            dao.deleteItem(item)
        }
    }

    override suspend fun markAsRecovered(id: Int) {
        withContext(Dispatchers.IO) {
            dao.markAsRecovered(id)
        }
    }
}
