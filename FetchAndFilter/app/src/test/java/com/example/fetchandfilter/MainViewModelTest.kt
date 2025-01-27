package com.example.fetchandfilter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fetchandfilter.network.FetchRepository
import com.example.fetchandfilter.network.ItemInfo
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.whenever
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = UnconfinedTestDispatcher()

    private val mockRepository: FetchRepository = mock()

    private lateinit var subject: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        subject = MainViewModel(mockRepository)
    }

    @Test
    fun `should filter items with null names`() {
        val input = listOf(
            ItemInfo(id = 1, listId = 1, name = "NAME"),
            ItemInfo(id = 2, listId = 2, name = null)
        )

        subject.processItemsResponse(input)
        val result = subject.listItems.value

        assertEquals(1, result?.size)
        assertEquals(1, result?.get(0)?.id)
    }

    @Test
    fun `should filter items with empty names`() {
        val input = listOf(
            ItemInfo(id = 1, listId = 1, name = ""),
            ItemInfo(id = 2, listId = 2, name = "NAME")
        )

        subject.processItemsResponse(input)
        val result = subject.listItems.value

        assertEquals(1, result?.size)
        assertEquals(2, result?.get(0)?.id)
    }

    @Test
    fun `should sort list by listId then name`() {
        val input = listOf(
            ItemInfo(id = 4, listId = 1, name = "Item 5"), // Should be 2nd
            ItemInfo(id = 3, listId = 2, name = "Item 4"), // Should be 4th
            ItemInfo(id = 2, listId = 1, name = "Item 3"), // Should be 1st
            ItemInfo(id = 1, listId = 2, name = "Item 2") // Should be 3rd
        )

        subject.processItemsResponse(input)
        val result = subject.listItems.value

        assertEquals(4, result?.size)

        assertEquals(2, result?.get(0)?.id)
        assertEquals("Item 3", result?.get(0)?.name)
        assertEquals(1, result?.get(0)?.listId)

        assertEquals(4, result?.get(1)?.id)
        assertEquals("Item 5", result?.get(1)?.name)
        assertEquals(1, result?.get(1)?.listId)

        assertEquals(1, result?.get(2)?.id)
        assertEquals("Item 2", result?.get(2)?.name)
        assertEquals(2, result?.get(2)?.listId)

        assertEquals(3, result?.get(3)?.id)
        assertEquals("Item 4", result?.get(3)?.name)
        assertEquals(2, result?.get(3)?.listId)
    }

    @Test
    fun `should return items from server`() = runTest(dispatcher) {
        val mockList = listOf(ItemInfo(id = 1, listId = 1, name = "NAME"))
        val mockResponse: Response<List<ItemInfo>> = mock()

        whenever(mockResponse.body()).thenReturn(mockList)
        whenever(mockResponse.isSuccessful).thenReturn(true)
        whenever(mockRepository.getItemsFromNetwork()).thenReturn(mockResponse)

        subject.getItemList()
        val result = subject.listItems.value

        assertEquals(1, result?.size)
    }

    @Test
    fun `should show error message when API call fails`() = runTest(dispatcher) {
        val mockResponse: Response<List<ItemInfo>> = mock()
        whenever(mockResponse.isSuccessful).thenReturn(false)
        whenever(mockResponse.message()).thenReturn("FAILURE_MESSAGE")
        whenever(mockRepository.getItemsFromNetwork()).thenReturn(mockResponse)

        subject.getItemList()
        val result = subject.errorMessage.value

        assertEquals("FAILURE_MESSAGE", result)
    }

    @Test
    fun `show error when exception thrown in API call`() = runTest(dispatcher) {
        doAnswer {
            throw NullPointerException()
        }.whenever(mockRepository).getItemsFromNetwork()

        subject.getItemList()

        assertEquals(NETWORK_ERROR_MESSAGE, subject.errorMessage.value)
    }

    @Test
    fun `show error when exception thrown in sorting or filtering process`() {
        val mockItem: ItemInfo = mock()

        doAnswer {
            throw NullPointerException()
        }.whenever(mockItem).name

        subject.processItemsResponse(listOf(mockItem))

        assertEquals(FILTER_ERROR_MESSAGE, subject.errorMessage.value)
    }
}