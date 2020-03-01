package com.example.weatherapp.data.utils

import com.example.weatherapp.data.local.models.CityLocalProperty
import com.example.weatherapp.data.remote.models.CityProperty


fun asDomainModel(list : List<CityLocalProperty>) : List<CityProperty> {
    return list.map {
        CityProperty(
            id = it.id,
            base = it.base,
            clouds = it.clouds,
            cod = it.cod,
            coord = it.coord,
            dt = it.dt,
            main = it.main,
            name = it.name,
            sys = it.sys,
            timezone = it.timezone,
            visibility = it.visibility,
            weather = it.weather,
            wind = it.wind
        )
    }
}

fun asDatabaseModel(it: CityProperty) : CityLocalProperty {
    return CityLocalProperty(
        id = it.id,
        base = it.base,
        clouds = it.clouds,
        cod = it.cod,
        coord = it.coord,
        dt = it.dt,
        main = it.main,
        name = it.name,
        sys = it.sys,
        timezone = it.timezone,
        visibility = it.visibility,
        weather = it.weather,
        wind = it.wind
    )
}