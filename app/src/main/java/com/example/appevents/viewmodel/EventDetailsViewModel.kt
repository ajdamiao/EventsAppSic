package com.example.appevents.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appevents.data.repository.EventRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventDetailsViewModel(private val repository: EventRepositoryImpl): ViewModel() {
    val eventDetailsLiveData = MutableLiveData<Any>()

    fun getEventDetails(id: String) {
        viewModelScope.launch {
            val eventDetail = withContext(Dispatchers.Default) {
                repository.getEventDetails(id)
            }
            eventDetailsLiveData.value = eventDetail
        }
    }
}