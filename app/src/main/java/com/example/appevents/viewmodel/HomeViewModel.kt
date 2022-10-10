package com.example.appevents.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appevents.data.repository.EventRepositoryImpl
import com.example.appevents.model.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: EventRepositoryImpl): ViewModel() {
    private val eventListMutableLiveData = MutableLiveData<ArrayList<Event>>()
    val eventListLiveData: LiveData<ArrayList<Event>> = eventListMutableLiveData

    fun getEvent() {
        CoroutineScope(Dispatchers.Main).launch {
            val events = withContext(Dispatchers.Default) {
                repository.getEvent()
            }

            eventListMutableLiveData.value = events.body()
        }
    }
}