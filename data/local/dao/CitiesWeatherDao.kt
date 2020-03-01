package com.example.weatherapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherapp.data.local.models.CityLocalProperty
import com.example.weatherapp.data.remote.models.CityProperty

@Dao
interface CitiesWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(vararg cityLocalProperty: CityLocalProperty)

    @Query("Delete from CITIES_WEATHER_TABLE WHERE id =:id")
    fun deleteCity(id : Int)

    @Query("DELETE FROM CITIES_WEATHER_TABLE")
    fun deleteAll()

    @Query("Select * from cities_weather_table")
    fun getCitiesWeather() : LiveData<List<CityLocalProperty>>
}