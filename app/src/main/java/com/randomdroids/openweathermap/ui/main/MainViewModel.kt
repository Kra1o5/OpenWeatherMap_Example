package com.randomdroids.openweathermap.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomdroids.data.common.Response
import com.randomdroids.data.common.Status
import com.randomdroids.domain.Location
import com.randomdroids.domain.Weather
import com.randomdroids.openweathermap.di.IoDispatcher
import com.randomdroids.usecases.GetLocationUseCase
import com.randomdroids.usecases.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @IoDispatcher private val requestDispatcher: CoroutineDispatcher,
    private val getLocationUseCase: GetLocationUseCase,
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: MutableStateFlow<Boolean> get() = _loading

    private val _weather = MutableStateFlow<List<Weather>>(emptyList())
    val weather: StateFlow<List<Weather>> get() = _weather

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> get() = _error

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> get() = _location

    fun requestLocation() {
        viewModelScope.launch {
            viewModelScope.launch(requestDispatcher) {
                _loading.value = true
                val result = getLocationUseCase.invoke()
                when (result.status) {
                    Status.LOADING -> _location.emit(null)
                    Status.SUCCESS -> onSuccessGetLocationData(result.data)
                    Status.ERROR -> onErrorGetLocationData()
                }
                _loading.value = false
            }
        }
    }

    /**
     * Function to handle when request succeeds
     *
     * @param location List of weather data
     */
    private fun onSuccessGetLocationData(location: Location?) {
        viewModelScope.launch(requestDispatcher) {
            _location.emit(location)
        }
    }

    /**
     * Function to handle when request fails
     *
     */
    private fun onErrorGetLocationData() {
        viewModelScope.launch(requestDispatcher) {
            _error.emit(true)
        }
    }

    fun requestListWeather(latitude: Double?, longitude: Double?) {
        viewModelScope.launch(requestDispatcher) {
            _loading.value = true
            val result = getWeatherUseCase.invoke(latitude, longitude)
            when (result.status) {
                Status.LOADING -> _weather.emit(emptyList())
                Status.SUCCESS -> onSuccessGetWeatherData(result)
                Status.ERROR -> onErrorGetWeatherData()
            }
            _loading.value = false
        }
    }

    /**
     * Function to handle when request succeeds
     *
     * @param weather List of weather data
     */
    private fun onSuccessGetWeatherData(weather: Response<List<Weather>>) {
        viewModelScope.launch(requestDispatcher) {
            weather.data?.let { _weather.emit(it) }
        }
    }

    /**
     * Function to handle when request fails
     *
     */
    private fun onErrorGetWeatherData() {
        viewModelScope.launch(requestDispatcher) {
            _error.emit(true)
        }
    }
}