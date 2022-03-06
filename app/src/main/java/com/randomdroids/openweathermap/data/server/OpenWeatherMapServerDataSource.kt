package com.randomdroids.openweathermap.data.server

import com.randomdroids.data.common.Response
import com.randomdroids.data.source.RemoteDataSource
import com.randomdroids.domain.Weather
import javax.inject.Inject

class OpenWeatherMapServerDataSource @Inject constructor(
    private val openWeatherServerService: OpenWeatherServerService
) : RemoteDataSource {

    override suspend fun getWeather(latitude: String, longitude: String): Response<List<Weather>> {
        return try {
            openWeatherServerService.getWeather(latitude, longitude)
        } catch (exception: Exception) {
            Response.error(exception.stackTraceToString(), null)
        }
    }
}