package com.randomdroids.openweathermap.data.server

import com.randomdroids.data.common.Response
import com.randomdroids.domain.Weather
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.coroutines.resumeWithException
import com.randomdroids.openweathermap.data.server.ServerConstants.LATITUDE_PARAM
import com.randomdroids.openweathermap.data.server.ServerConstants.LONGITUDE_PARAM
import com.randomdroids.openweathermap.formatDate
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class OpenWeatherServerService @Inject constructor(private val baseurl: String) {

    suspend fun getWeather(latitude: String, longitude: String): Response<List<Weather>> {
        return suspendCancellableCoroutine { continuation ->
            try {
                val reader: BufferedReader
                val url = URL("$baseurl&$LATITUDE_PARAM=$latitude&$LONGITUDE_PARAM=$longitude")

                with(url.openConnection() as HttpsURLConnection) {
                    requestMethod = "GET"
                    reader = BufferedReader(InputStreamReader(inputStream))

                    val response = StringBuffer()
                    var inputLine = reader.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = reader.readLine()
                    }
                    reader.close()

                    if (continuation.isActive) {
                        continuation.resume(parseJson(response.toString()), onCancellation = null)
                    }
                    disconnect()
                }

            } catch (exception: Exception) {
                exception.printStackTrace()
                if (continuation.isActive) {
                    continuation.resumeWithException(exception)
                }
            }

        }
    }

    private fun parseJson(jsonString: String): Response<List<Weather>> {
        val weatherList: MutableList<Weather> = ArrayList()
        return try {
            val jsonObj = JSONObject(jsonString)
            val arrayField: JSONArray = jsonObj.getJSONArray("hourly")
            for (item in 0 until arrayField.length()) {
                val field = arrayField.getJSONObject(item)
                weatherList.add(
                    Weather(
                        formatDate(field.getLong("dt")),
                        field.getDouble("temp").roundToInt()
                    )
                )
            }
            Response.success(weatherList)
        } catch (exception: JSONException) {
            Response.error(
                "Error parsing file $exception.stackTraceToString()", null
            )
        }
    }
}