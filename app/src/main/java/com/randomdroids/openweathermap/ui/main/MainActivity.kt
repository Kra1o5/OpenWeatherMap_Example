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
import com.randomdroids.openweathermap.databinding.ActivityMainBinding
import com.randomdroids.openweathermap.databinding.ActivityMainBinding.inflate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


/**
 * Main activity.
 *
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapClickListener,
    OnMapLongClickListener, OnMapReadyCallback {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var mMap: GoogleMap

    companion object {
        private const val DEFAULT_ZOOM = 15f
    }

    /**
     * Check if all needed permissions are already granted and show info dialog if isn't
     */
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            var isGranted = false

            permissions.entries.forEach {
                isGranted = it.value
            }

            if (isGranted) {
                viewModel.requestLocation()
            } else {
                showAlertDialog()
            }
        }

    /**
     * On create.
     *
     * @param savedInstanceState Saved instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflate(layoutInflater)
        setContentView(binding.root)

        permissionChecker()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val adapter = MainAdapter()
        binding.recyclerview.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.loading.onEach {
                /** Setting the status of the progress bar */
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

    /**
     * Get location and show it in map with a marker.
     *
     * @param latitude Latitude value
     * @param longitude Longitude value
     */
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

    /**
     * On map ready, set on map click listeners.
     *
     * @param googleMap Google map instance
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener(this)
        mMap.setOnMapLongClickListener(this)
    }

    /**
     * On map click, get coordinates and show his weather information.
     *
     * @param point get user selected coordinates
     */
    override fun onMapClick(point: LatLng) {
        getLocationData(point)
    }

    /**
     * On map long click, get coordinates and show his weather information.
     *
     * @param point get user selected coordinates
     */
    override fun onMapLongClick(point: LatLng) {
        getLocationData(point)
    }

    /**
     * Permission checker.
     */
    private fun permissionChecker() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    /**
     * Show alert dialog to inform the user about the required permissions and handle accordingly his response.
     */
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

    /**
     * Get location data and request his weather information.
     *
     * @param point get user coordinates
     */
    private fun getLocationData(point: LatLng) {
        getLocation(point.latitude, point.longitude)
        viewModel.requestListWeather(point.latitude, point.longitude)
    }
}