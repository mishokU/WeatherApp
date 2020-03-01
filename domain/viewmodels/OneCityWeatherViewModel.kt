package com.example.weatherapp.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.local.database.CitiesWeatherDatabase
import com.example.weatherapp.data.remote.models.CityProperty
import com.example.weatherapp.data.repo.AllCitiesWeatherRepo
import kotlinx.coroutines.*

class OneCityWeatherViewModel(cityWeather: CityProperty,application: Application) : AndroidViewModel(application) {

    private val _weather = MutableLiveData<CityProperty>()
    val weather: LiveData<CityProperty>
        get() = _weather

    private val _hasThisCity = MutableLiveData<Boolean>()
    val hasThisCity: LiveData<Boolean>
        get() = _hasThisCity

    /* This is the job for all coroutines started by this ViewModel.  */
    private var viewModelJob = Job()
    /*This is the main scope for all coroutines launched by MainViewModel. */
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: AllCitiesWeatherRepo = AllCitiesWeatherRepo(CitiesWeatherDatabase.getDatabase(application))

    val rememberWeatherProperty = repository.allCities

    init {
        _weather.value = cityWeather
    }

    fun insert(city: CityProperty) = coroutineScope.launch {
        repository.insert(city)
    }

    fun delete(cityProperty: CityProperty) = coroutineScope.launch {
        repository.delete(cityProperty)
    }

    fun hasCurrentCity(
        it: List<CityProperty?>,
        cityProperty: CityProperty
    ) {
        for(element in it){
            if(element?.name == cityProperty.name){
                _hasThisCity.value = true
                break
            } else {
                _hasThisCity.value = false
                break
            }
        }
    }

    fun hasCurrentCityComplete() {
        _hasThisCity.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}