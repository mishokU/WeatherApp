package com.example.weatherapp.data.local.converters

import androidx.room.TypeConverter
import com.example.weatherapp.data.remote.models.Coord

class CoordConverter  {

    @TypeConverter
    fun fromCoord(coord: Coord) : String {
        return coord.lat.toString() + "," + coord.lon.toString()
    }

    @TypeConverter
    fun toCoord(coords : String) : Coord{
        val list = coords.split(",").map {it.trim()}
        return Coord(list[0].toDouble(),list[1].toDouble())
    }
}