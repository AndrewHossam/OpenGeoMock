package com.andrewhossam.opengeomock.ui

interface MapManager {
    fun initialize(container: Any)

    fun moveCamera(
        latitude: Double,
        longitude: Double,
        zoomLevel: Double,
    )

    fun setLocationEnabled(enabled: Boolean)

    fun addMarker(
        latitude: Double,
        longitude: Double,
        title: String,
    )

    fun setOnMapClickListener(listener: (latitude: Double, longitude: Double) -> Unit)

    fun destroy()
}
