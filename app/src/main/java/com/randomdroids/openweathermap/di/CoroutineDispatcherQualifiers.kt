package com.randomdroids.openweathermap.di

import javax.inject.Qualifier

/**
 * Io dispatcher.
 *
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher

/**
 * Main dispatcher.
 *
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatcher