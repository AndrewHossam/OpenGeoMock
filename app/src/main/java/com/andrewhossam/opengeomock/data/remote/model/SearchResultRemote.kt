package com.andrewhossam.opengeomock.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultRemote(
    @SerialName("features")
    val features: List<Feature>,
    @SerialName("type")
    val type: String,
) {
    @Serializable
    data class Feature(
        @SerialName("geometry")
        val geometry: Geometry,
        @SerialName("type")
        val type: String,
        @SerialName("properties")
        val properties: Properties,
    ) {
        @Serializable
        data class Geometry(
            @SerialName("coordinates")
            val coordinates: List<Double>,
            @SerialName("type")
            val type: String,
        )

        @Serializable
        data class Properties(
            @SerialName("osm_type")
            val osmType: String,
            @SerialName("osm_id")
            val osmId: Int,
            @SerialName("extent")
            val extent: List<Double>?,
            @SerialName("country")
            val country: String,
            @SerialName("osm_key")
            val osmKey: String,
            @SerialName("countrycode")
            val countrycode: String,
            @SerialName("osm_value")
            val osmValue: String,
            @SerialName("name")
            val name: String,
            @SerialName("type")
            val type: String,
            @SerialName("city")
            val city: String?,
            @SerialName("postcode")
            val postcode: String?,
            @SerialName("locality")
            val locality: String?,
            @SerialName("street")
            val street: String?,
            @SerialName("district")
            val district: String?,
            @SerialName("county")
            val county: String?,
            @SerialName("state")
            val state: String?,
        )
    }
}
