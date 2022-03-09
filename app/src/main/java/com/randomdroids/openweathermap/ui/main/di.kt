package com.randomdroids.openweathermap.ui.main

import com.randomdroids.data.repository.LocationRepository
import com.randomdroids.data.repository.OpenWeatherMapRepository
import com.randomdroids.usecases.GetLocationUseCase
import com.randomdroids.usecases.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Main activity module.
 *
 * Define use cases for Main Activity
 */
@Module
@InstallIn(ViewModelComponent::class)
class MainActivityModule {
    /**
     * Get location use case provider.
     *
     * @param locationRepository Location repository
     * @return location repository information
     */
    @Provides
    @ViewModelScoped
    fun getLocationUseCaseProvider(locationRepository: LocationRepository) =
        GetLocationUseCase(locationRepository)

    /**
     * Get weather use case provider.
     *
     * @param openWeatherMapRepository Open weather map repository
     * @return open weather map repository information
     */
    @Provides
    @ViewModelScoped
    fun getWeatherUseCaseProvider(openWeatherMapRepository: OpenWeatherMapRepository) =
        GetWeatherUseCase(openWeatherMapRepository)
}