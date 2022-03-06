package com.randomdroids.openweathermap.di

import android.app.Application
import com.randomdroids.data.source.LocationDataSource
import com.randomdroids.data.source.RemoteDataSource
import com.randomdroids.openweathermap.data.local.LocationLocalDataSource
import com.randomdroids.openweathermap.data.server.API_KEY
import com.randomdroids.openweathermap.data.server.OpenWeatherMapServerDataSource
import com.randomdroids.openweathermap.data.server.OpenWeatherServerService
import com.randomdroids.openweathermap.data.server.ServerConstants.API_PARAM
import com.randomdroids.openweathermap.data.server.ServerConstants.EXCLUDE_PARAM
import com.randomdroids.openweathermap.data.server.ServerConstants.UNITS_PARAM
import com.randomdroids.openweathermap.data.server.ServerConstants.URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideBaseUrl(): String {
        return "$URL?$EXCLUDE_PARAM&$UNITS_PARAM&$API_PARAM=$API_KEY"
    }

    @Provides
    fun openWeatherMapDataSourceProvider(openWeatherServerService: OpenWeatherServerService): RemoteDataSource =
        OpenWeatherMapServerDataSource(openWeatherServerService)

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        LocationLocalDataSource(app)
}