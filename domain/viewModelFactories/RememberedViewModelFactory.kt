package com.example.weatherapp.domain.viewModelFactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.domain.viewmodels.RememberedCityViewModel

class RememberedViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RememberedCityViewModel::class.java)) {
            return RememberedCityViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}