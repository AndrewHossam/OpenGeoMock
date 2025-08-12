package com.andrewhossam.opengeomock.feature.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrewhossam.opengeomock.domain.GetRecentSearchUseCase
import com.andrewhossam.opengeomock.domain.GetSearchResultUseCase
import com.andrewhossam.opengeomock.domain.SaveRecentSearchUseCase
import com.andrewhossam.opengeomock.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import kotlin.time.Duration.Companion.milliseconds

@KoinViewModel
class MapViewModel(
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val getRecentSearchUseCase: GetRecentSearchUseCase,
    private val saveRecentSearchUseCase: SaveRecentSearchUseCase,
    private val navigator: Navigator,
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchResult = MutableStateFlow<List<String>>(emptyList())
    val searchResult = _searchResult.asStateFlow()

    val recentSearchResult =
        getRecentSearchUseCase()
            .onEach { result ->
                _searchResult.value = result
            }.stateIn(
                scope = viewModelScope,
                started = kotlinx.coroutines.flow.SharingStarted.Lazily,
                initialValue = emptyList(),
            )

    private fun saveRecentSearch(query: String) {
        viewModelScope.launch {
            saveRecentSearchUseCase(query)
        }
    }

    fun updateSearchQuery(query: String) {
        viewModelScope.launch {
            _searchQuery.value = query
        }
    }

    fun performSearch() {
        viewModelScope.launch {
            saveRecentSearch(_searchQuery.value)
            getSearchResultUseCase(_searchQuery.value)
                .debounce(500.milliseconds)
                .onEach { result ->
//                    _searchResult.value = result
                }.launchIn(this)
        }
    }
}
