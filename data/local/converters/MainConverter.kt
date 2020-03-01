package com.example.weatherapp.data.local.converters

import androidx.room.TypeConverter
import com.example.weatherapp.data.remote.models.Main

class MainConverter {

    @TypeConverter
    fun fromMain(main: Main) : String{
        return main.feels_like.toString() + "," +
                main.humidity.toString() + "," +
                main.pressure.toString() + "," +
                main.temp.toString() + "," +
                main.temp_max.toString() + "," +
                main.temp_min.toString()
    }

    @TypeConverter
    fun toMain(main : String) : Main {
        val list = main.split(",").map {it.trim()}
        return Main(
            list[0].toDouble(),
            list[1].toInt(),
            list[2].toInt(),
            list[3].toDouble(),
            list[4].toDouble(),
            list[5].toDouble()
        )
    }
}