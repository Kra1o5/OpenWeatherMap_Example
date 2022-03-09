package com.randomdroids.usecases

import com.randomdroids.data.common.Response
import com.randomdroids.data.repository.OpenWeatherMapRepository
import com.randomdroids.domain.Weather

/**
 * Get weather use case.
 *
 * @property openWeatherMapRepository
 */
class GetWeatherUseCase(private val openWeatherMapRepository: OpenWeatherMapRepository) {
    /**
     * Invoke.
     *
     * @param latitude Latitude
     * @param longitude Longitude
     * @return weather information list response
     */
    suspend fun invoke(
        latitude: Double?,
        longitude: Double?
    ): Response<List<Weather>> =
        openWeatherMapRepository.getWeather(latitude, longitude)
}