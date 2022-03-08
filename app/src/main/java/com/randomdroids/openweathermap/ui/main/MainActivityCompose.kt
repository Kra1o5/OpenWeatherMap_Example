package com.randomdroids.openweathermap.ui.main

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import com.randomdroids.openweathermap.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainActivityCompose : ComponentActivity() {
    /** View model */
    private val viewModel: MainViewModel by viewModels()

    companion object {
        private const val DEFAULT_ZOOM = 15f
    }

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
                //
            }
        }

    private fun permissionChecker() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun getLocation(latitude: Double?, longitude: Double?) {
        val lastLocation = LatLng(latitude!!, longitude!!)
        /*with(mMap) {
            clear()
            addMarker(
                MarkerOptions()
                    .position(lastLocation)
            )
            moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, DEFAULT_ZOOM))
        }*/
    }

     private fun getLocationData(point: LatLng) {
        getLocation(point.latitude, point.longitude)
        viewModel.requestListWeather(point.latitude, point.longitude)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionChecker()

        viewModel.requestListWeather(1.35, 103.87)

        setContent {
            MainCompose()
        }

        lifecycleScope.launchWhenStarted {
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
        }


        /* private fun getLocation(latitude: Double?, longitude: Double?) {
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
    }*/
    }
}