package com.randomdroids.openweathermap

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Format date.
 *
 * @param time Time
 * @return a human readable hour
 */
fun formatDate(time: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.ROOT)
    val date = Date(time * 1000)
    return sdf.format(date)
}