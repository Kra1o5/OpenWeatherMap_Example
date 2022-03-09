package com.randomdroids.openweathermap.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.randomdroids.domain.Weather
import com.randomdroids.openweathermap.R
import com.randomdroids.openweathermap.databinding.TableLayoutBinding
import kotlin.properties.Delegates

/**
 * Main adapter.
 *
 * @param weatherDataList list with weather information
 */
class MainAdapter(weatherDataList: List<Weather> = emptyList()) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var getWeatherDataList = weatherDataList

    var weatherDataList by Delegates.observable(weatherDataList) { _, _, newWeatherData ->
        getWeatherDataList = newWeatherData
        notifyDataSetChanged()
    }

    /**
     * On create view holder.
     *
     * @param viewGroup View group
     * @param viewType View type
     * @return view holder
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.table_layout, viewGroup, false)

        return ViewHolder(view)
    }

    /**
     * Get item count.
     *
     * @return items count of the weather list
     */
    override fun getItemCount(): Int = getWeatherDataList.size

    /**
     * On bind view holder.
     *
     * @param holder Holder
     * @param position Position
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherData = weatherDataList[position]
        holder.bind(weatherData)
    }

    /**
     * View holder.
     *
     * @param view
     */
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