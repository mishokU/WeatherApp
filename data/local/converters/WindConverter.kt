package com.example.weatherapp.data.local.converters

import androidx.room.TypeConverter
import com.example.weatherapp.data.remote.models.Wind

class WindConverter {

    @TypeConverter
    fun fromWind(wind: Wind) : String{
        return wind.deg.toString() + "," + wind.speed.toString()
    }

    @TypeConverter
    fun toWind(wind : String) : Wind {
        val list = wind.split(",").map{ it.trim()}
        return Wind(list[0].toDouble(), list[1].toDouble())
    }
}