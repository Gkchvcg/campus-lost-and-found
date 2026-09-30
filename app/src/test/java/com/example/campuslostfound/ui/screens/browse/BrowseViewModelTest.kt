package com.example.campuslostfound.ui.screens.browse

import com.example.campuslostfound.data.local.entity.ItemEntity
import com.example.campuslostfound.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class BrowseViewModelTest {

    private lateinit var viewModel: BrowseViewModel
    private lateinit var repository: ItemRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(ItemRepository::class.java)
        
        val items = listOf(
            ItemEntity(id = 1, title = "Lost Keys", description = "", category = "Keys", type = "LOST", location = "Library", date = "", time = "", imageUri = null, contactInfo = "", postedDate = 0L),
            ItemEntity(id = 2, title = "Found Wallet", description = "", category = "Accessories", type = "FOUND", location = "Cafeteria", date = "", time = "", imageUri = null, contactInfo = "", postedDate = 0L)
        )
        
        `when`(repository.getAllItems()).thenReturn(flowOf(items))
        `when`(repository.getItemsByType("LOST")).thenReturn(flowOf(items.filter { it.type == "LOST" }))
        `when`(repository.getItemsByType("FOUND")).thenReturn(flowOf(items.filter { it.type == "FOUND" }))
        
        viewModel = BrowseViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state loads all items`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals("ALL", viewModel.selectedType.value)
        assertEquals(2, viewModel.items.value.size)
    }

    @Test
    fun `onTypeSelected updates selectedType and filters items`() = runTest {
        viewModel.onTypeSelected("LOST")
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertEquals("LOST", viewModel.selectedType.value)
        assertEquals(1, viewModel.items.value.size)
        assertEquals("Lost Keys", viewModel.items.value[0].title)
    }
}
