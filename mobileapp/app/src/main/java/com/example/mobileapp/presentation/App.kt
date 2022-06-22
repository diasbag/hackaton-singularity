package com.example.mobileapp.presentation

import android.app.Application
import com.example.mobileapp.di.mainModule
import com.example.mobileapp.di.networkModule
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin { modules(mainModule, networkModule) }
    }
}