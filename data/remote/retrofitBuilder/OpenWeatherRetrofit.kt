package com.example.weatherapp.data.remote.retrofitBuilder

import com.example.weatherapp.data.remote.weatherApi.moshi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL = "https://api.openweathermap.org"

const val APIKEY = "0b2991ed0fd468c155b11db345670237"

const val IMG_PATH = "https://openweathermap.org/img/w/"

val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()