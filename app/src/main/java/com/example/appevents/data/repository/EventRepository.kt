package com.example.appevents.data.repository

import com.example.appevents.data.EventsApi
import com.example.appevents.model.CheckIn
import com.example.appevents.model.Event
import retrofit2.Response
import java.util.ArrayList

interface EventRepository {

    fun makeRequest(): EventsApi

    suspend fun getEvent(): Response<ArrayList<Event>>

    suspend fun getEventDetails(id: String): Event

    suspend fun checkInEvent(checkIn: CheckIn): Response<Any>
}