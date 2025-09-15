package com.example.careermate_app

import android.app.Application
import com.example.careermate_app.network.ApiClient

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiClient.init(this) // initialize retrofit with context
    }
}