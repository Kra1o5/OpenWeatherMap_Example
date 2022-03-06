package com.randomdroids.data.source

import com.randomdroids.data.common.Response
import com.randomdroids.domain.Location


interface LocationDataSource {
    suspend fun getLocation(): Response<Location>
}