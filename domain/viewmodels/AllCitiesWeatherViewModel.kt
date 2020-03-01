package com.example.weatherapp.domain.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.data.remote.models.CityProperty
import com.example.weatherapp.data.remote.retrofitBuilder.API_KEY
import com.example.weatherapp.data.remote.weatherApi.CityWeatherApi
import com.example.weatherapp.domain.utils.ConnectionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class AllCitiesWeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val _tmpWeatherCitiesProp = mutableListOf<CityProperty>()
    private val _weatherCitiesProperty = MutableLiveData<List<CityProperty>>()
    val weatherCitiesProperty: LiveData<List<CityProperty>>
        get() = _weatherCitiesProperty

    private val _showFullCityWeather = MutableLiveData<CityProperty>()
    val showFullCityWeather: LiveData<CityProperty>
        get() = _showFullCityWeather

    private val _networkStatus = MutableLiveData<ConnectionStatus>()
    val networkStatus: LiveData<ConnectionStatus>
        get() = _networkStatus

    /* This is the job for all coroutines started by this ViewModel.  */
    private var viewModelJob = Job()
    /*This is the main scope for all coroutines launched by MainViewModel. */
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private fun getCityProperty(filter : String?){
        coroutineScope.launch {
            if (filter != null) {
                val getCurrentCityDiffered = CityWeatherApi.retrofitService.getCityAsync(filter, API_KEY)
                try {
                    _networkStatus.value = ConnectionStatus.LOADING
                    val result = getCurrentCityDiffered.await()
                    if (!containSameElement(result)) {
                        _networkStatus.value = ConnectionStatus.DONE
                        _tmpWeatherCitiesProp.add(result)
                        _weatherCitiesProperty.value = _tmpWeatherCitiesProp
                    } else {
                        _networkStatus.value = ConnectionStatus.DONE
                    }
                } catch (e: Throwable) {
                    Log.e("network", e.message!!)
                    _networkStatus.value = ConnectionStatus.ERROR
                }
            }
        }
    }

    private fun containSameElement(cityProperty: CityProperty) : Boolean{
        if(_tmpWeatherCitiesProp.isNotEmpty()) {
            for(element in _tmpWeatherCitiesProp){
                if(element.name == cityProperty.name) {
                    return true
                }
            }
        }
        return false
    }

    fun setSearchedCityName(city : String){
        getCityProperty(city)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayCityWithWeather(it: CityProperty?) {
        _showFullCityWeather.value = it
    }

    fun displayPropertyDetailsComplete() {
        _showFullCityWeather.value = null
    }
}