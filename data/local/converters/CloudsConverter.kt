package com.example.weatherapp.data.local.converters

import androidx.room.TypeConverter
import com.example.weatherapp.data.remote.models.Clouds

class CloudsConverter {

    @TypeConverter
    fun fromClouds(clouds: Clouds) : Int {
        return clouds.all
    }
    @TypeConverter
    fun toClouds(all : Int) : Clouds{
        return Clouds(all)
    }
}