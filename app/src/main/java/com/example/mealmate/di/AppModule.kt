package com.example.mealmate.di

import com.example.mealmate.data.Repository
import com.example.mealmate.data.remote.ApiService
import com.example.mealmate.ui.detail.DetailViewModel
import com.example.mealmate.ui.home.HomeViewModel
import com.example.mealmate.util.RxSchedulers
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory   // <-- ADD THIS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // 🔑 Use Moshi's Kotlin adapter so data classes (MealsResponse, etc.) are parsed correctly
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())      // <-- THIS LINE FIXES THE CRASH
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }
    single { Repository(get()) }
    single { RxSchedulers() }

    viewModel { HomeViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
}