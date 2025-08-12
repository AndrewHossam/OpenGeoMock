package com.andrewhossam.opengeomock.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.location.provider.ProviderProperties
import android.os.Build
import android.os.SystemClock
import org.koin.core.annotation.Factory

@Factory
class MockLocationImpl(
    private val context: Context,
) : MockLocation {
    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val providerName = LocationManager.GPS_PROVIDER

    override fun startMocking(
        latitude: Double,
        longitude: Double,
    ) {
        locationManager.safeAddTestProvider(providerName)

        locationManager.setTestProviderEnabled(providerName, true)

        val mockLocation =
            Location(providerName).apply {
                this.latitude = latitude
                this.longitude = longitude
                this.altitude = 0.0
                this.time = System.currentTimeMillis()
                this.accuracy = 5f
                this.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()
            }

        locationManager.setTestProviderLocation(providerName, mockLocation)
    }

    override fun stopMocking() {
        if (locationManager.allProviders.contains(providerName)) {
            locationManager.removeTestProvider(providerName)
        }
    }

    @SuppressLint("WrongConstant")
    fun LocationManager.safeAddTestProvider(providerName: String) {
        if (allProviders.contains(providerName).not()) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            addTestProvider(
                providerName,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                ProviderProperties.POWER_USAGE_LOW,
                ProviderProperties.ACCURACY_FINE,
            )
        } else {
            @Suppress("DEPRECATION")
            addTestProvider(
                providerName,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                Criteria.POWER_LOW,
                Criteria.ACCURACY_FINE,
            )
        }
    }
}
