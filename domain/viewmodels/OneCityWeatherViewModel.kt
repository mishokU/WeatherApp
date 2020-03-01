package com.example.weatherapp.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.database.CitiesWeatherDatabase
import com.example.weatherapp.data.local.models.CityLocalProperty
import com.example.weatherapp.data.remote.models.CityProperty
import com.example.weatherapp.data.repo.AllCitiesWeatherRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OneCityWeatherViewModel(cityWeather: CityProperty,application: Application) : AndroidViewModel(application) {

    private val _weather = MutableLiveData<CityProperty>()
    val weather: LiveData<CityProperty>
        get() = _weather

    /* This is the job for all coroutines started by this ViewModel.  */
    private var viewModelJob = Job()
    /*This is the main scope for all coroutines launched by MainViewModel. */
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: AllCitiesWeatherRepo = AllCitiesWeatherRepo(CitiesWeatherDatabase.getDatabase(application, coroutineScope))

    init {
        _weather.value = cityWeather
    }

    fun insert(city: CityProperty) = coroutineScope.launch {
        repository.insert(city)
    }

}