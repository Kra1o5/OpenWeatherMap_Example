package com.randomdroids.openweathermap.data.server

object ServerConstants {
    private const val EXCLUDE_VALUES = "current,minutely,daily,alerts"
    private const val UNITS_VALUE = "metric"

    const val URL = "https://api.openweathermap.org/data/2.5/onecall"
    const val LATITUDE_PARAM = "lat"
    const val LONGITUDE_PARAM = "lon"
    const val EXCLUDE_PARAM = "exclude=$EXCLUDE_VALUES"
    const val UNITS_PARAM = "units=$UNITS_VALUE"
    const val API_PARAM = "appid"
}