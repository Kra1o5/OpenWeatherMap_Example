package com.randomdroids.data.repository

import com.randomdroids.data.common.Response
import com.randomdroids.data.source.LocationDataSource
import com.randomdroids.domain.Location

/**
 * Location repository.
 *
 * @property locationDataSource from fusedlocation
 */
class LocationRepository(private val locationDataSource: LocationDataSource) {
    /**
     * Get location.
     *
     * @return location response
     */
    suspend fun getLocation(): Response<Location> {
        return try {
            locationDataSource.getLocation()
        } catch (exception: Exception) {
            Response.error(exception.stackTraceToString(), null)
        }
    }
}