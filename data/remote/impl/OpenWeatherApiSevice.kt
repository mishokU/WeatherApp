package com.example.weatherapp.data.remote.impl

import com.example.weatherapp.data.remote.models.CityProperty
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {

    @GET("/data/2.5/weather")
    fun getCityAsync(@Query("q")name: String, @Query("appid") api_key: String) : Deferred<CityProperty>
}