package com.example.weatherapp.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.local.database.CitiesWeatherDatabase
import com.example.weatherapp.data.remote.models.CityProperty
import com.example.weatherapp.data.repo.AllCitiesWeatherRepo
import kotlinx.coroutines.*

class RememberedCityViewModel(application: Application) : AndroidViewModel(application) {

    /* This is the job for all coroutines started by this ViewModel.  */
    private var viewModelJob = Job()
    /*This is the main scope for all coroutines launched by MainViewModel. */
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // The ViewModel maintains a reference to the repository to get data.
    private val database = CitiesWeatherDatabase.getDatabase(application)
    private val repository: AllCitiesWeatherRepo = AllCitiesWeatherRepo(database)

    private val _showFullCityWeather = MutableLiveData<CityProperty>()
    val showFullCityWeather: LiveData<CityProperty>
        get() = _showFullCityWeather

    fun deleteAll() = coroutineScope.launch{
        repository.deleteAll()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getAllCities(){
        coroutineScope.launch {
            val cities = repository.allCities
            try {

            } catch (t : Throwable){

            }
        }
    }

    val rememberWeatherProperty = repository.allCities

    fun displayCityWithWeather(it: CityProperty?) {
        _showFullCityWeather.value = it
    }

    fun displayPropertyDetailsComplete() {
        _showFullCityWeather.value = null
    }

    fun refreshAll(it: List<CityProperty?>) {
        coroutineScope.launch {
            repository.refreshData(it)
        }
    }
}