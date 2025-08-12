package com.andrewhossam.opengeomock.data

import com.andrewhossam.opengeomock.domain.model.SearchResultDomain
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(query: String): Flow<SearchResultDomain>

    fun searchByCoordinates(
        lat: Double,
        lon: Double,
    ): Flow<SearchResultDomain>

    suspend fun saveSearch(query: String)

    fun getRecentSearches(): Flow<List<String>>
}
