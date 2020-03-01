package com.example.weatherapp.data.remote.retrofitBuilder

import com.example.weatherapp.data.remote.weatherApi.moshi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL = "https://api.openweathermap.org"

//c89a47fb9f5a79bb5779eb22acf3da70
const val API_KEY = "c89a47fb9f5a79bb5779eb22acf3da70"

const val IMG_PATH = "https://openweathermap.org/img/w/"

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()