package com.randomdroids.openweathermap.di

import com.randomdroids.data.repository.LocationRepository
import com.randomdroids.data.repository.OpenWeatherMapRepository
import com.randomdroids.data.source.LocationDataSource
import com.randomdroids.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Data module.
 *
 * Define all repositories with his sources
 */
@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun openWeatherMapRepositoryProvider(
        remoteDataSource: RemoteDataSource
    ) = OpenWeatherMapRepository(remoteDataSource)

    @Provides
    fun locationRepositoryProvider(
        locationDataSource: LocationDataSource
    ) = LocationRepository(locationDataSource)
}