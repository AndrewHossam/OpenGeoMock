package com.andrewhossam.opengeomock.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.koin.compose.koinInject
import org.koin.core.annotation.Single
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapLibreMapOptions
import org.maplibre.android.maps.MapView

@Single
class MapLibreManager : MapManager {
    private lateinit var mapView: MapView
    private lateinit var mapLibreMap: MapLibreMap

    override fun initialize(container: Any) {
        if (container is MapView) {
            mapView = container
            mapView.getMapAsync { mapbox ->
                mapLibreMap = mapbox
            }
        }
    }

    override fun setLocationEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun addMarker(
        latitude: Double,
        longitude: Double,
        title: String,
    ) {
        TODO("Not yet implemented")
    }

    override fun setOnMapClickListener(listener: (latitude: Double, longitude: Double) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun destroy() {
        TODO("Not yet implemented")
    }

    override fun moveCamera(
        latitude: Double,
        longitude: Double,
        zoomLevel: Double,
    ) {
        mapLibreMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), zoomLevel),
        )
    }
}

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    mapManager: MapManager = koinInject(),
) {
    val context = LocalContext.current
    val mapLibreOptions =
        MapLibreMapOptions.createFromAttributes(context).apply {
            apiBaseUri("https://api.maplibre.org")
            camera(
                CameraPosition
                    .Builder()
                    .target(LatLng(37.4233438, -122.0728817))
                    .zoom(1.0)
                    .build(),
            )
            zoomGesturesEnabled(true)
            compassEnabled(true)
            compassFadesWhenFacingNorth(true)
            scrollGesturesEnabled(true)
            rotateGesturesEnabled(true)
            logoEnabled(false)
        }

    val lifecycleOwner = LocalLifecycleOwner.current.lifecycle
    val mapView = remember { MapView(context, mapLibreOptions) } // can be more abstracted

    DisposableEffect(lifecycleOwner) {
        val observer =
            LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_START -> mapView.onStart()
                    Lifecycle.Event.ON_RESUME -> mapView.onResume()
                    Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                    Lifecycle.Event.ON_STOP -> mapView.onStop()
                    Lifecycle.Event.ON_DESTROY -> {
                        mapView.onDestroy()
                        mapManager.destroy()
                    }

                    else -> {}
                }
            }

        lifecycleOwner.addObserver(observer)
        onDispose {
            lifecycleOwner.removeObserver(observer)
        }
    }

    mapManager.initialize(mapView)

    AndroidView(
        factory = { mapView },
        modifier = modifier,
    )
}
