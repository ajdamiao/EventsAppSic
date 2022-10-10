package com.example.appevents.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.appevents.data.repository.EventRepositoryImpl
import com.example.appevents.model.CheckIn
import com.example.appevents.model.Event
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
import org.koin.androidx.viewmodel.scope.emptyState
import retrofit2.Response
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CheckInViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<EventRepositoryImpl>()
    private lateinit var viewModel: CheckInViewModel
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
    fun `asd` () {
        testCoroutineScope.runBlockingTest {

            coEvery { repository.checkInEvent(any()) } returns Response.success(emptyState())

            viewModel.doCheckIn(CheckIn("1", "Teste", "teste@teste.com"))

            val result = viewModel.checkInLiveData.getOrAwaitValue()

            assertEquals(true, result)

            coVerify { repository.checkInEvent(any()) }

        }
    }

    private fun instantiateViewModel(): CheckInViewModel {
        return CheckInViewModel(repository)
    }
}