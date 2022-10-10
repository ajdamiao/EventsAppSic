package com.example.appevents.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.appevents.data.EventsApi
import com.example.appevents.data.repository.EventRepositoryImpl
import com.example.appevents.model.Event
import com.example.appevents.utiltest.getOrAwaitValue
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<EventRepositoryImpl>()
    private lateinit var homeViewModel: HomeViewModel
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    lateinit var testCoroutineScope: TestCoroutineScope

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        homeViewModel = instantiateViewModel()
        testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel fetches Event then it should return event list`() {
        val eventMockedList: ArrayList<Event> = ArrayList()
        eventMockedList.add(Event("1", "Evento 1", 1231321,emptyList(), "1.11", "Evento 1 descricao", "evento 1 imagem", -123.32,32.202))
        eventMockedList.add(Event("2", "Evento 2", 1231321,emptyList(), "2.22", "Evento 2 descricao", "evento 2 imagem", -123.32,32.202))
        eventMockedList.add(Event("3", "Evento 3", 1231321,emptyList(), "3.33", "Evento 3 descricao", "evento 3 imagem", -123.32,32.202))

        testCoroutineScope.runBlockingTest {

            coEvery { repository.getEvent() } returns Response.success(eventMockedList)

            homeViewModel.getEvent()

            val result = homeViewModel.eventLiveData.getOrAwaitValue()

            assertEquals(eventMockedList, result)

            coVerify { repository.getEvent() }
        }
    }

    private fun instantiateViewModel(): HomeViewModel {
        return HomeViewModel(repository)
    }
}