package com.example.weatherapp.data.local.converters

import androidx.room.TypeConverter
import com.example.weatherapp.data.remote.models.Sys

class SysConverter {

    @TypeConverter
    fun fromSys(sys: Sys) : String{
        return sys.country + "," +
                sys.id.toString() + "," +
                sys.sunrise.toString() + "," +
                sys.sunset.toString() + "," +
                sys.type.toString()
    }

    @TypeConverter
    fun toSys(sys : String) : Sys{
        val list = sys.split(",").map{it.trim()}
        return Sys(
            list[0],
            list[1].toInt(),
            list[2].toInt(),
            list[3].toInt(),
            list[4].toInt()
        )
    }
}