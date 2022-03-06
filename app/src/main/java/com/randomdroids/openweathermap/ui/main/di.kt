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

@Module
@InstallIn(ViewModelComponent::class)
class MainActivityModule {
    @Provides
    @ViewModelScoped
    fun getLocationUseCaseProvider(locationRepository: LocationRepository) =
        GetLocationUseCase(locationRepository)

    @Provides
    @ViewModelScoped
    fun getWeatherUseCaseProvider(openWeatherMapRepository: OpenWeatherMapRepository) =
        GetWeatherUseCase(openWeatherMapRepository)
}