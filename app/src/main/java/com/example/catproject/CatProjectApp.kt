package com.example.catproject

import android.app.Application
import android.content.Context
import com.example.catproject.data.di.AppComponent
import com.example.catproject.data.di.DaggerAppComponent

class CatProjectApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is CatProjectApp -> appComponent
        else -> this.applicationContext.appComponent
    }