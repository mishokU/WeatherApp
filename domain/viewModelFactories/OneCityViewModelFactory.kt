package com.example.weatherapp.domain.viewModelFactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.remote.models.CityProperty
import com.example.weatherapp.domain.viewmodels.AllCitiesWeatherViewModel
import com.example.weatherapp.domain.viewmodels.OneCityWeatherViewModel

class OneCityViewModelFactory(
    private val cityWeather : CityProperty,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OneCityWeatherViewModel::class.java)) {
            return OneCityWeatherViewModel(cityWeather, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}