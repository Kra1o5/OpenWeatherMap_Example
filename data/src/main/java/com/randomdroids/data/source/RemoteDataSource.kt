package com.randomdroids.data.source

import com.randomdroids.data.common.Response
import com.randomdroids.domain.Weather

interface RemoteDataSource {
    suspend fun getWeather(latitude: String, longitude: String): Response<List<Weather>>
}