package com.andrewhossam.opengeomock

import android.app.Application
import com.andrewhossam.opengeomock.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import org.maplibre.android.MapLibre

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(
                AppModule().module,
            )
        }

        MapLibre.getInstance(this)
    }
}
