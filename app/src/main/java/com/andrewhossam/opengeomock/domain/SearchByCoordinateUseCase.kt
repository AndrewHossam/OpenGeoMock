package com.andrewhossam.opengeomock.domain

import com.andrewhossam.opengeomock.data.SearchRepository
import org.koin.core.annotation.Factory

@Factory
class SearchByCoordinateUseCase(
    private val searchRepository: SearchRepository,
) {
    operator fun invoke(
        lat: Double,
        lon: Double,
    ) = searchRepository.searchByCoordinates(
        lat = lat,
        lon = lon,
    )
}
