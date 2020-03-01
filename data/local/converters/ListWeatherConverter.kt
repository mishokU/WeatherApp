package com.example.weatherapp.data.local.converters

import androidx.room.TypeConverter
import com.example.weatherapp.data.remote.models.Weather

class ListWeatherConverter {

    @TypeConverter
    fun fromListWeather(list : List<Weather>) : String {
        return list[0].description + "," +
                list[0].icon + "," +
                list[0].id.toString() + "," +
                list[0].main
    }

    @TypeConverter
    fun toListWeather(listWeather: String) : List<Weather> {
        val list = listWeather.split(",").map{it.trim()}
        return listOf(
            Weather(
                list[0],
                list[1],
                list[2].toInt(),
                list[3]
            )
        )
    }
}