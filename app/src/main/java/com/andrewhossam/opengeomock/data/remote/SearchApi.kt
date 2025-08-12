package com.andrewhossam.opengeomock.data.remote

import com.andrewhossam.opengeomock.data.remote.model.SearchResultRemote
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Single

@Single
class SearchApi(
    private val httpClient: HttpClient,
) {
    suspend fun searchLocation(query: String): SearchResultRemote =
        httpClient
            .get("api") {
                parameter("q", query)
                parameter("limit", 5)
            }.body()

    suspend fun searchByCoordinates(
        lat: Double,
        lon: Double,
    ): SearchResultRemote =
        httpClient
            .get("reverse") {
                parameter("lat", lat)
                parameter("lon", lon)
            }.body()
}
