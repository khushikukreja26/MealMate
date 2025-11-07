package com.example.mealmate

import android.app.Application
import com.example.mealmate.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MealMateApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MealMateApp)
            modules(appModule)
        }
    }
}