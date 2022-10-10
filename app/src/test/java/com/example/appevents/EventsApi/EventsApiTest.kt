package com.example.appevents.EventsApi

import com.example.appevents.data.EventsApi
import com.example.appevents.model.CheckIn
import com.example.appevents.model.Event
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class EventsApiTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val apiService: EventsApi = mockk()

    companion object {
        private val JSON_APPLICATION = MediaType.get("application/json")
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when fetch getEvent is called then it should call apiEvent getEvents and return successful`() {
        val eventMockedList: ArrayList<Event> = ArrayList()
        eventMockedList.add(Event("1", "Evento 1", 1231321,emptyList(), "1.11", "Evento 1 descricao", "evento 1 imagem", -123.32,32.202))
        eventMockedList.add(Event("2", "Evento 2", 1231321,emptyList(), "2.22", "Evento 2 descricao", "evento 2 imagem", -123.32,32.202))
        eventMockedList.add(Event("3", "Evento 3", 1231321,emptyList(), "3.33", "Evento 3 descricao", "evento 3 imagem", -123.32,32.202))


        coEvery { apiService.getEvents() } returns Response.success(eventMockedList)

        runBlockingTest {
            apiService.getEvents()
        }

        coVerify { apiService.getEvents() }
    }

    @Test
    fun `when fetch getEvent is called then it should call apiEvent getEvents and return event list`() {
        val eventMockedList: ArrayList<Event> = ArrayList()
        eventMockedList.add(Event("1", "Evento 1", 1231321,emptyList(), "1.11", "Evento 1 descricao", "evento 1 imagem", -123.32,32.202))
        eventMockedList.add(Event("2", "Evento 2", 1231321,emptyList(), "2.22", "Evento 2 descricao", "evento 2 imagem", -123.32,32.202))
        eventMockedList.add(Event("3", "Evento 3", 1231321,emptyList(), "3.33", "Evento 3 descricao", "evento 3 imagem", -123.32,32.202))


        coEvery { apiService.getEvents().body() } returns eventMockedList

        runBlockingTest {
            apiService.getEvents()
        }

        coVerify { apiService.getEvents() }
    }

    @Test
    fun `when fetch getEventDetails is called then it should call apiEvent getEventDetails and return event details` () {
        val event = Event("3", "Evento 3", 1231321,emptyList(), "3.33", "Evento 3 descricao", "evento 3 imagem", -123.32,32.202)

        coEvery { apiService.getEventDetail("3") } returns event

        runBlockingTest {
            apiService.getEventDetail("3")
        }

        coVerify { apiService.getEventDetail("3") }
    }

    @Test
    fun `when fetch getEventDetails is called then it should call apiEvent getEventDetails and return successful` () {
        val event = Event("3", "Evento 3", 1231321,emptyList(), "3.33", "Evento 3 descricao", "evento 3 imagem", -123.32,32.202)

        coEvery { apiService.getEventDetail(any()) } returns event

        runBlockingTest {
            apiService.getEventDetail("3")
        }

        coVerify { apiService.getEventDetail("3") }
    }

    @Test
    fun `when fetch getEvent is called then it should call apiEvent getEvents and return eventList` () = runBlockingTest {
        val eventMockedList: ArrayList<Event> = ArrayList()
        eventMockedList.add(Event("1", "Evento 1", 1231321,emptyList(), "1.11", "Evento 1 descricao", "evento 1 imagem", -123.32,32.202))
        eventMockedList.add(Event("2", "Evento 2", 1231321,emptyList(), "2.22", "Evento 2 descricao", "evento 2 imagem", -123.32,32.202))
        eventMockedList.add(Event("3", "Evento 3", 1231321,emptyList(), "3.33", "Evento 3 descricao", "evento 3 imagem", -123.32,32.202))

        coEvery { apiService.getEvents() } returns Response.success(eventMockedList)

        val events = apiService.getEvents()

        assertEquals(3,events.body()?.size)
        assertEquals(eventMockedList[0], events.body()?.get(0))
        assertEquals(eventMockedList[1], events.body()?.get(1))
        assertEquals(eventMockedList[2], events.body()?.get(2))
    }

    @Test
    fun `when fetch checkin is called then it should call apiEvent checkin and return successful`() = runBlockingTest {
        val checkIn = CheckIn("1", "Teste", "teste@teste.com")

        coEvery { apiService.checkInEvent(any()) } returns Response.success(null)
        val response = apiService.checkInEvent(checkIn)

        assertTrue(response.isSuccessful)
    }
}