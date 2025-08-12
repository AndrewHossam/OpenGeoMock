package com.andrewhossam.opengeomock.domain.model

data class SearchResultDomain(
    val features: List<FeatureDomain>,
    val type: String,
) {
    data class FeatureDomain(
        val geometry: GeometryDomain,
        val type: String,
        val properties: PropertiesDomain,
    ) {
        data class GeometryDomain(
            val coordinates: List<Double>,
            val type: String,
        )

        data class PropertiesDomain(
            val osmType: String,
            val osmId: Int,
            val extent: List<Double>?,
            val country: String,
            val osmKey: String,
            val countrycode: String,
            val osmValue: String,
            val name: String,
            val type: String,
            val city: String?,
            val postcode: String?,
            val locality: String?,
            val street: String?,
            val district: String?,
        )
    }
}
