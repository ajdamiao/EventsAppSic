package com.example.appevents.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appevents.data.repository.EventRepositoryImpl
import com.example.appevents.model.CheckIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckInViewModel(private val repository: EventRepositoryImpl): ViewModel() {
    private val _checkInMutableLiveData = MutableLiveData<Any>()
    val checkInLiveData: LiveData<Any> = _checkInMutableLiveData

    fun doCheckIn(checkIn: CheckIn) {
        viewModelScope.launch {
            val checkInResponse = withContext(Dispatchers.Default) {
                repository.checkInEvent(checkIn)
            }
            _checkInMutableLiveData.value = checkInResponse.isSuccessful
        }
    }
}
