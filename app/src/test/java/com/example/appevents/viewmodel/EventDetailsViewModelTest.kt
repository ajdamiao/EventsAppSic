package com.example.appevents.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.appevents.data.EventsApi
import com.example.appevents.data.repository.EventRepositoryImpl
import com.example.appevents.model.Event
import com.example.appevents.util.ResourceManager
import com.example.appevents.utiltest.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class EventDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<EventRepositoryImpl>()
    private val resourceManager = mockk<ResourceManager>()
    private lateinit var viewModel: EventDetailsViewModel
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    lateinit var testCoroutineScope: TestCoroutineScope

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = instantiateViewModel()
        testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel fetches EventDetails then it should return one event detail`() {
        val event = Event("1", "Evento 1", 1231321,emptyList(), "1.11", "Evento 1 descricao", "evento 1 imagem", -123.32,32.202)

        testCoroutineScope.runBlockingTest {

            coEvery { repository.getEventDetails(any()) } returns event

            viewModel.getEventDetails("1")

            val result = viewModel.eventDetailsLiveData.getOrAwaitValue()

            assertEquals(event, result)

            coVerify { repository.getEventDetails(any()) }
        }
    }

    private fun instantiateViewModel(): EventDetailsViewModel {
        return EventDetailsViewModel(repository, resourceManager)
    }
}