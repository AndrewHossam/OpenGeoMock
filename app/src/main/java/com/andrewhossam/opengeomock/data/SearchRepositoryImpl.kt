package com.andrewhossam.opengeomock.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.andrewhossam.opengeomock.data.remote.SearchApi
import com.andrewhossam.opengeomock.domain.mapper.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class SearchRepositoryImpl(
    private val searchApi: SearchApi,
    private val localDataSource: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher,
) : SearchRepository {
    override fun search(query: String) =
        flow {
            val result = searchApi.searchLocation(query)
            emit(result.toDomain())
        }.flowOn(dispatcher)

    override fun searchByCoordinates(
        lat: Double,
        lon: Double,
    ) = flow {
        val result = searchApi.searchByCoordinates(lat, lon)
        emit(result.toDomain())
    }.flowOn(dispatcher)

    override suspend fun saveSearch(query: String) {
        localDataSource.edit { preferences ->
            preferences[PreferencesKeys.RECENT_SEARCHES] = query
        }
    }

    override fun getRecentSearches() =
        // Simulate a network call
        localDataSource.data
            .map { preferences ->
                val recentSearches = preferences[PreferencesKeys.RECENT_SEARCHES] ?: ""
                recentSearches.split(",").filter { it.isNotEmpty() }
            }

    companion object {
        private object PreferencesKeys {
            val RECENT_SEARCHES = stringPreferencesKey("recent_searches")
        }
    }
}
