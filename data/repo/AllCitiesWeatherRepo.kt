package com.example.weatherapp.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.weatherapp.data.local.database.CitiesWeatherDatabase
import com.example.weatherapp.data.local.models.CityLocalProperty
import com.example.weatherapp.data.remote.models.CityProperty
import com.example.weatherapp.data.utils.asDatabaseModel
import com.example.weatherapp.data.utils.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    // TODO Create refresh current cities
    suspend fun refreshData() {
        withContext(Dispatchers.IO){

        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO){
            database.cityWeatherDao().deleteAll()
        }
    }


}

