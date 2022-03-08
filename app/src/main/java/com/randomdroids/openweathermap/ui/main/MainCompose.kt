package com.randomdroids.openweathermap.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.randomdroids.openweathermap.R


val singapore = LatLng(1.35, 103.87)

@Composable
fun MainCompose() {
    val viewModel: MainViewModel = viewModel()
    val errorState = viewModel.error.collectAsState().value
    val locationData = viewModel.location.collectAsState().value
    val loadingState = viewModel.loading.collectAsState().value
    val mapCoord = if (locationData != null) LatLng(
        locationData.latitude!!,
        locationData.longitude!!
    ) else singapore
    val weatherData = viewModel.weather.collectAsState().value.sortedBy { it.temperature }

    var isMapLoaded by remember { mutableStateOf(false) }
    // Observing and controlling the camera's state can be done with a CameraPositionState
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 11f)
    }

    ConstraintLayout {
        // Create references for the composables to constrain
        val (mapBox, text, progressBar) = createRefs()
        Box(modifier = Modifier
            .constrainAs(mapBox) {
                top.linkTo(parent.top)
            }
            .fillMaxHeight(0.5f)) {
            GoogleMapView(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapClick = {
                    singapore
                },
                onMapLongClick = { singapore },
                onMapLoaded = { isMapLoaded = true }
            )
        }

        // The LazyColumn will be our table. Notice the use of the weights below
        LazyColumn(
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(mapBox.bottom)
                }
                .fillMaxHeight(0.5f)
                .padding(20.dp, 0.dp, 0.dp, 0.dp)
        ) {
            items(weatherData.size) {
                Row(Modifier.fillMaxWidth(1.0f)) {
                    Text(
                        text = stringResource(id = R.string.temperature_text),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth(0.30f)
                            .wrapContentHeight(Alignment.CenterVertically)
                    )
                    Text(
                        text = stringResource(
                            id = R.string.temperature_value,
                            weatherData[it].temperature!!
                        ),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth(0.15f)
                            .wrapContentHeight(Alignment.CenterVertically)
                    )
                    Text(

                        text = stringResource(id = R.string.hour_text),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth(0.25f)
                            .wrapContentHeight(Alignment.CenterVertically)
                    )
                    Text(
                        text = weatherData[it].hour!!,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth(0.30f)
                            .wrapContentHeight(Alignment.CenterVertically)
                    )
                }
            }
        }

        AnimatedVisibility(
            modifier = Modifier.constrainAs(progressBar) {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            },
            visible = loadingState,
            enter = EnterTransition.None,
            exit = fadeOut()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .background(Color.Transparent)
                    .wrapContentSize()
            )
        }
    }
}

@Composable
fun GoogleMapView(
    modifier: Modifier,
    cameraPositionState: CameraPositionState,
    googleMapOptionsFactory: () -> GoogleMapOptions = { GoogleMapOptions() },
    onMapClick: (LatLng) -> Unit,
    onMapLongClick: (LatLng) -> Unit,
    onMapLoaded: () -> Unit
) {
    var uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
    var mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    val viewModel: MainViewModel = viewModel()

    val context = LocalContext.current
    val mapView = remember { MapView(context, googleMapOptionsFactory()) }


    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = uiSettings,
        onMapClick = onMapClick,
        onMapLongClick = onMapLongClick,
        onMapLoaded = onMapLoaded
    ) {

    }
}

@Composable
fun alertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            properties = DialogProperties(),
            text = {
                Text(stringResource(id = R.string.permission_text))
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        )
    }
}