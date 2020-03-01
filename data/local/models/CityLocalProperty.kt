package com.example.weatherapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.weatherapp.data.local.converters.*
import com.example.weatherapp.data.remote.models.*
import com.squareup.moshi.JsonClass

@Entity(tableName = "cities_weather_table")
data class CityLocalProperty(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val base: String,

    @TypeConverters(CloudsConverter::class)
    val clouds: Clouds,

    val cod: Int,

    @TypeConverters(CoordConverter::class)
    val coord: Coord,
    val dt: Int,

    @TypeConverters(MainConverter::class)
    val main: Main,
    val name: String,


    @TypeConverters(SysConverter::class)
    val sys: Sys,

    val timezone: Int,
    val visibility: Int,

    @TypeConverters(ListWeatherConverter::class)
    val weather: List<Weather>,

    @TypeConverters(WindConverter::class)
    val wind: Wind
)
