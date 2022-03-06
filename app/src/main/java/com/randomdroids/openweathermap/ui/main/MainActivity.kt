package com.randomdroids.openweathermap.ui.main

import android.Manifest
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.randomdroids.openweathermap.R
import com.randomdroids.openweathermap.common.alertDialog
import com.randomdroids.openweathermap.databinding.ActivityMainBinding.inflate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapClickListener,
    OnMapLongClickListener, OnMapReadyCallback {
    /** View model */
    private val viewModel: MainViewModel by viewModels()

    private lateinit var mMap: GoogleMap
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)

    companion object {
        private const val DEFAULT_ZOOM = 15f
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            permissions.entries.forEach {
                val isGranted = it.value
                if (isGranted) {
                    viewModel.requestLocation()
                } else {
                    showAlertDialog()
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = inflate(layoutInflater)
        setContentView(binding.root)

        permissionChecker()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

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

            viewModel.location.onEach {
                if (it != null) {
                    getLocationData(LatLng(it.latitude!!, it.longitude!!))
                }

            }.launchIn(this)

            viewModel.weather.onEach {
                adapter.weatherDataList = it.sortedBy { weatherData -> weatherData.temperature }
            }.launchIn(this)
        }
    }

    private fun getLocation(latitude: Double?, longitude: Double?) {
        val lastLocation = LatLng(latitude!!, longitude!!)
        with(mMap) {
            clear()
            addMarker(
                MarkerOptions()
                    .position(lastLocation)
            )
            moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, DEFAULT_ZOOM))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener(this)
        mMap.setOnMapLongClickListener(this)
    }

    override fun onMapClick(point: LatLng) {
        getLocationData(point)
    }

    override fun onMapLongClick(point: LatLng) {
        getLocationData(point)
    }

    private fun permissionChecker() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun showAlertDialog() {
        alertDialog(
            this,
            getString(R.string.permission_text),
            { _, which ->
                if (which == DialogInterface.BUTTON_POSITIVE)
                    permissionChecker()
            },
            { _, which ->
                if (which == DialogInterface.BUTTON_NEGATIVE)
                    finish()
            }
        )
    }

    private fun getLocationData(point: LatLng) {
        getLocation(point.latitude, point.longitude)
        viewModel.requestListWeather(point.latitude, point.longitude)
    }
}