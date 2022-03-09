package com.randomdroids.data.source

import com.randomdroids.data.common.Response
import com.randomdroids.domain.Weather

/**
 * Remote data source.
 */
interface RemoteDataSource {
    /**
     * Get weather.
     *
     * @param latitude Latitude
     * @param longitude Longitude
     * @return list weather information
     */
    suspend fun getWeather(latitude: Double?, longitude: Double?): Response<List<Weather>>
}