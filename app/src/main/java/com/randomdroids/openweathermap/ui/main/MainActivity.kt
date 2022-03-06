package com.randomdroids.openweathermap.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.randomdroids.openweathermap.R
import com.randomdroids.openweathermap.databinding.ActivityMainBinding.inflate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    /** View model */
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MainAdapter()
        binding.recyclerview.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.loading.onEach {
                // Setting the status of the progress bar
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }.launchIn(this)

            viewModel.error.onEach {
                if (it) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.error_text),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }.launchIn(this)

            // TODO: Obtain from Google Maps
            viewModel.requestListWeather("40.42898", "-3.70495")

            viewModel.weather.onEach {
                adapter.weatherDataList = it.sortedBy { weatherData -> weatherData.temperature }
            }.launchIn(this)
        }
    }
}