package com.example.campuslostfound.data.repository

import com.example.campuslostfound.data.local.dao.ItemDao
import com.example.campuslostfound.data.local.entity.ItemEntity
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ItemRepositoryImplTest {

    private class FakeItemDao : ItemDao {
        val items = mutableListOf<ItemEntity>()

        override fun getAllItems() = flowOf(items)
        override fun getItemById(id: Int) = flowOf(items.find { it.id == id })
        override fun getItemsByType(type: String) = flowOf(items.filter { it.type == type })
        override fun searchItems(query: String) = flowOf(items.filter { it.title.contains(query) })
        
        override fun insertItem(item: ItemEntity): Long {
            val itemWithId = if (item.id == 0) item.copy(id = items.size + 1) else item
            items.add(itemWithId)
            return itemWithId.id.toLong()
        }

        override fun updateItem(item: ItemEntity): Int {
            val idx = items.indexOfFirst { it.id == item.id }
            if (idx >= 0) {
                items[idx] = item
                return 1
            }
            return 0
        }

        override fun deleteItem(item: ItemEntity): Int {
            return if (items.removeIf { it.id == item.id }) 1 else 0
        }

        override fun markAsRecovered(id: Int) {
            val idx = items.indexOfFirst { it.id == id }
            if (idx >= 0) {
                items[idx] = items[idx].copy(recovered = true)
            }
        }
    }

    private lateinit var dao: FakeItemDao
    private lateinit var repository: ItemRepositoryImpl

    @Before
    fun setup() {
        dao = FakeItemDao()
        repository = ItemRepositoryImpl(dao)
    }

    @Test
    fun `insertItem adds item to repository`() = runBlocking {
        val item = ItemEntity(title = "Test", description = "", category = "", type = "LOST", location = "", date = "", time = "", imageUri = null, contactInfo = "", postedDate = 0)
        val id = repository.insertItem(item)
        assertEquals(1, dao.items.size)
        assertEquals(1L, id)
    }
}
