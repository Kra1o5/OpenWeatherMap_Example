package com.randomdroids.usecases

import com.randomdroids.data.common.Response
import com.randomdroids.data.repository.LocationRepository
import com.randomdroids.domain.Location

class GetLocationUseCase(private val locationRepository: LocationRepository) {
    suspend fun invoke(
    ): Response<Location> =
        locationRepository.getLocation()
}