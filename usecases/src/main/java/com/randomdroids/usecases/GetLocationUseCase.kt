package com.randomdroids.usecases

import com.randomdroids.data.common.Response
import com.randomdroids.data.repository.LocationRepository
import com.randomdroids.domain.Location

/**
 * Get location use case.
 *
 * @property locationRepository
 */
class GetLocationUseCase(private val locationRepository: LocationRepository) {
    /**
     * Invoke.
     *
     * @return location response
     */
    suspend fun invoke(
    ): Response<Location> =
        locationRepository.getLocation()
}