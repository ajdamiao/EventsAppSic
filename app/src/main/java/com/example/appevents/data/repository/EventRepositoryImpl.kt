package com.example.appevents.data.repository

import android.os.Build
import com.example.appevents.BuildConfig
import com.example.appevents.data.EventsApi
import com.example.appevents.model.CheckIn
import com.example.appevents.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class EventRepositoryImpl: EventRepository {
    private lateinit var baseUrl: String
    private val timeout : Long = 60

    override fun makeRequest(): EventsApi {
        baseUrl = if(Build.VERSION.SDK_INT <= 21){
            BuildConfig.Base_URL19
        }else {
            BuildConfig.Base_URL
        }

        val client = OkHttpClient.Builder()
            .readTimeout(timeout, TimeUnit.SECONDS)
            .build()

        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventsApi::class.java)
    }

    override suspend fun getEvent(): Response<ArrayList<Event>> {
        return withContext(Dispatchers.IO) {
            makeRequest().getEvents()
        }
    }

    override suspend fun getEventDetails(id: String): Event {
        return withContext(Dispatchers.IO) {
            makeRequest().getEventDetail(id)
        }
    }

    override suspend fun checkInEvent(checkIn: CheckIn): Response<Any> {
        return withContext(Dispatchers.IO) {
            makeRequest().checkInEvent(checkIn)
        }
    }
}
