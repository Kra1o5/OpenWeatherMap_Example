package com.randomdroids.usecases

import com.randomdroids.data.common.Response
import com.randomdroids.data.repository.OpenWeatherMapRepository
import com.randomdroids.domain.Weather

class GetWeatherUseCase(private val openWeatherMapRepository: OpenWeatherMapRepository) {
    suspend fun invoke(
        latitude: Double?,
        longitude: Double?
    ): Response<List<Weather>> =
        openWeatherMapRepository.getWeather(latitude, longitude)
}