package com.andrewhossam.opengeomock.domain.mapper

import com.andrewhossam.opengeomock.data.remote.model.SearchResultRemote
import com.andrewhossam.opengeomock.domain.model.SearchResultDomain

fun SearchResultRemote.toDomain() =
    SearchResultDomain(
        features = features.map { it.toDomain() },
        type = type,
    )

private fun SearchResultRemote.Feature.toDomain() =
    SearchResultDomain.FeatureDomain(
        geometry = geometry.toDomain(),
        type = type,
        properties = properties.toDomain(),
    )

private fun SearchResultRemote.Feature.Geometry.toDomain() =
    SearchResultDomain.FeatureDomain.GeometryDomain(
        coordinates = coordinates,
        type = type,
    )

private fun SearchResultRemote.Feature.Properties.toDomain() =
    SearchResultDomain.FeatureDomain.PropertiesDomain(
        osmType = osmType,
        osmId = osmId,
        extent = extent,
        country = country,
        osmKey = osmKey,
        countrycode = countrycode,
        osmValue = osmValue,
        name = name,
        type = type,
        city = city,
        postcode = postcode,
        locality = locality,
        street = street,
        district = district,
    )
