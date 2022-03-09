package com.randomdroids.openweathermap.data.server

import com.randomdroids.data.common.Response
import com.randomdroids.data.source.RemoteDataSource
import com.randomdroids.domain.Weather
import javax.inject.Inject

/**
 * Open weather map server data source.
 *
 * @property openWeatherServerService
 */
class OpenWeatherMapServerDataSource @Inject constructor(
    private val openWeatherServerService: OpenWeatherServerService
) : RemoteDataSource {

    /**
     * Get weather.
     *
     * @param latitude Latitude
     * @param longitude Longitude
     * @return list weather information
     */
    override suspend fun getWeather(latitude: Double?, longitude: Double?): Response<List<Weather>> {
        return try {
            openWeatherServerService.getWeather(latitude, longitude)
        } catch (exception: Exception) {
            Response.error(exception.stackTraceToString(), null)
        }
    }
}