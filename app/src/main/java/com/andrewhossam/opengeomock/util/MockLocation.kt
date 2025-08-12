package com.andrewhossam.opengeomock.util

interface MockLocation {
    fun startMocking(
        latitude: Double,
        longitude: Double,
    )

    fun stopMocking()
}
