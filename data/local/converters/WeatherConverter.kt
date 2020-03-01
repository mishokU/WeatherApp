package com.example.weatherapp.data.local.converters

import androidx.room.TypeConverter
import com.example.weatherapp.data.remote.models.Weather

class WeatherConverter {

    @TypeConverter
    fun fromWeather(weather: Weather) : String {
        return weather.description + "," +
                weather.icon + "," +
                weather.id.toString() + "," +
                weather.main
    }

    @TypeConverter
    fun toWeather(weather : String) : Weather {
        val list = weather.split(",").map{it.trim()}
        return Weather(
            list[0],
            list[1],
            list[2].toInt(),
            list[3]
        )
    }
}