package com.randomdroids.data.repository

import com.randomdroids.data.common.Response
import com.randomdroids.data.source.RemoteDataSource
import com.randomdroids.domain.Weather

class OpenWeatherMapRepository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getWeather(latitude: String, longitude: String): Response<List<Weather>> {
        return try {
            remoteDataSource.getWeather(latitude, longitude)
        } catch (exception: Exception) {
            Response.error(exception.stackTraceToString(), null)
        }
    }
}