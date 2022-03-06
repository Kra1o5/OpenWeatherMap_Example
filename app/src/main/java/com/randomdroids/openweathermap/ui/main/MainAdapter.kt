package com.randomdroids.openweathermap.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.randomdroids.domain.Weather
import com.randomdroids.openweathermap.R
import com.randomdroids.openweathermap.databinding.TableLayoutBinding
import kotlin.properties.Delegates

class MainAdapter(weatherDataList: List<Weather> = emptyList()) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var getWeatherDataList = weatherDataList

    var weatherDataList by Delegates.observable(weatherDataList) { _, _, newWeatherData ->
        getWeatherDataList = newWeatherData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.table_layout, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = getWeatherDataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherData = weatherDataList[position]
        holder.bind(weatherData)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TableLayoutBinding.bind(view)
        fun bind(weather: Weather) = with(binding) {
            weatherTemperatureValue.text = itemView.resources.getString(
                R.string.temperature_value,
                weather.temperature
            )
            weatherHourValue.text = weather.hour
        }
    }
}