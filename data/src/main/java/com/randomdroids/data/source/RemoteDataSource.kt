package com.randomdroids.data.source

import com.randomdroids.data.common.Response
import com.randomdroids.domain.Weather

interface RemoteDataSource {
    suspend fun getWeather(latitude: Double?, longitude: Double?): Response<List<Weather>>
}