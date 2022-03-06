package com.randomdroids.openweathermap.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomdroids.data.common.Response
import com.randomdroids.data.common.Status
import com.randomdroids.domain.Weather
import com.randomdroids.openweathermap.di.IoDispatcher
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
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: MutableStateFlow<Boolean> get() = _loading

    private val _weather = MutableStateFlow<List<Weather>>(emptyList())
    val weather: StateFlow<List<Weather>> get() = _weather

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> get() = _error

    fun requestListWeather(latitude: String, longitude: String) {
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