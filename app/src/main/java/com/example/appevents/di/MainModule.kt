package com.example.appevents.di

import android.app.Application
import android.content.Context
import com.example.appevents.data.repository.EventRepositoryImpl
import com.example.appevents.util.CustomExceptions
import com.example.appevents.util.ResourceManager
import com.example.appevents.util.Util
import com.example.appevents.viewmodel.CheckInViewModel
import com.example.appevents.viewmodel.EventDetailsViewModel
import com.example.appevents.viewmodel.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single { EventRepositoryImpl() }

    single { Util() }

    single { CustomExceptions() }

    single {
        ResourceManager(
            context = androidContext()
        )
    }

    viewModel {
        HomeViewModel(
            repository = get()
        )
    }

    viewModel {
        EventDetailsViewModel(
            repository = get()
        )
    }

    viewModel {
        CheckInViewModel(
            repository = get()
        )
    }
}