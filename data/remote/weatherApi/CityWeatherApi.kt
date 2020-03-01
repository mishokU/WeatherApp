package com.example.weatherapp.data.remote.weatherApi

import com.example.weatherapp.data.remote.impl.OpenWeatherApiService
import com.example.weatherapp.data.remote.retrofitBuilder.retrofit
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object CityWeatherApi {
    val retrofitService : OpenWeatherApiService by lazy {
        retrofit.create(
            OpenWeatherApiService::class.java)
    }
}
