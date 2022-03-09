package com.randomdroids.data.repository

import com.randomdroids.data.common.Response
import com.randomdroids.data.source.RemoteDataSource
import com.randomdroids.domain.Weather

/**
 * Open weather map repository.
 *
 * @property remoteDataSource from OpenWeather server
 */
class OpenWeatherMapRepository(private val remoteDataSource: RemoteDataSource) {
    /**
     * Get weather.
     *
     * @param latitude Latitude
     * @param longitude Longitude
     * @return list weather information
     */
    suspend fun getWeather(latitude: Double?, longitude: Double?): Response<List<Weather>> {
        return try {
            remoteDataSource.getWeather(latitude, longitude)
        } catch (exception: Exception) {
            Response.error(exception.stackTraceToString(), null)
        }
    }
}