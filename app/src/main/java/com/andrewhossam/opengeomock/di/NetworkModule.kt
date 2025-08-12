package com.andrewhossam.opengeomock.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.maplibre.android.BuildConfig

@Module
@ComponentScan
class NetworkModule {
    @Single
    fun okHttpProvider(context: Context) =
        OkHttpClient
            .Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(ChuckerInterceptor(context))
                }
            }.build()

    @Single
    fun httpKtorClient(
        okHttpClient: OkHttpClient,
        json: Json,
    ) = HttpClient(OkHttp) {
        defaultRequest {
            url {
                host = BASE_URL
                protocol =
                    if (BuildConfig.DEBUG.not()) {
                        URLProtocol.HTTPS
                    } else {
                        URLProtocol.HTTP
                    }
            }
        }
        engine {
            preconfigured = okHttpClient
        }
        install(ContentNegotiation) {
            json(json)
        }
    }

    companion object {
        private const val BASE_URL = "photon.komoot.io/api/"
    }
}
