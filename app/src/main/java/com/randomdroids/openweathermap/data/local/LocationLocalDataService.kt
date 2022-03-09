package com.randomdroids.openweathermap.data.local

import android.annotation.SuppressLint
import android.app.Application
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.randomdroids.data.common.Response
import com.randomdroids.data.source.LocationDataSource
import com.randomdroids.domain.Location
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

/**
 * Location local data source.
 *
 * @param application context
 */
class LocationLocalDataSource @Inject constructor(
    application: Application
) : LocationDataSource {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    private var cancellationTokenSource = CancellationTokenSource()

    /**
     * Get location.
     *
     * @return user location coordinates
     */
    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): Response<Location> =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener { lastLocation ->
                    if (lastLocation.result != null) {
                        continuation.resume(
                            Response.success(
                                Location(
                                    lastLocation.result.latitude,
                                    lastLocation.result.longitude
                                )
                            ), null
                        )
                    } else {
                        fusedLocationClient.getCurrentLocation(
                            LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
                            cancellationTokenSource.token
                        ).addOnCompleteListener { currentLocation ->
                            if (currentLocation.result != null) {
                                continuation.resume(
                                    Response.success(
                                        Location(
                                            currentLocation.result.latitude,
                                            currentLocation.result.longitude
                                        )
                                    ), null
                                )
                            } else {
                                continuation.resume(Response.error("", null), null)
                            }
                        }
                    }
                }
        }
}