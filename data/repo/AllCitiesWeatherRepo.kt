package com.example.weatherapp.data.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.observe
import com.example.weatherapp.data.local.database.CitiesWeatherDatabase
import com.example.weatherapp.data.remote.models.CityProperty
import com.example.weatherapp.data.remote.retrofitBuilder.API_KEY
import com.example.weatherapp.data.remote.weatherApi.CityWeatherApi
import com.example.weatherapp.data.utils.asDatabaseModel
import com.example.weatherapp.data.utils.asDomainModel
import kotlinx.coroutines.*

class AllCitiesWeatherRepo(private val database: CitiesWeatherDatabase) {


    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    // Convert CityLocalProperty to UI object cityProperty

    val allCities: LiveData<List<CityProperty?>> = Transformations.map(database.cityWeatherDao().getCitiesWeather()) {
        asDomainModel(it)
    }

    suspend fun insert(word: CityProperty) {
        withContext(Dispatchers.IO) {
            database.cityWeatherDao().insertCity(asDatabaseModel(word))
        }
    }

    suspend fun refreshData(it: List<CityProperty?>) {
        withContext(Dispatchers.IO){
            try {
                for(element in it) {
                    val city = CityWeatherApi.retrofitService.getCityAsync(element!!.name, API_KEY).await()
                    database.cityWeatherDao().insertCity(asDatabaseModel(city))
                }
            } catch (t : Throwable){
                Log.d("refresh", t.message!!)
            }
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO){
            database.cityWeatherDao().deleteAll()
        }
    }

    suspend fun delete(cityProperty: CityProperty) {
        withContext(Dispatchers.IO){
            database.cityWeatherDao().deleteCity(cityProperty.id)
        }
    }


}

