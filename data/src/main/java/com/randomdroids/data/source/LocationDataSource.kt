package com.randomdroids.data.source

import com.randomdroids.data.common.Response
import com.randomdroids.domain.Location


/**
 * Location data source.
 */
interface LocationDataSource {
    /**
     * Get location.
     *
     * @return location response
     */
    suspend fun getLocation(): Response<Location>
}