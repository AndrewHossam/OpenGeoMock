package com.andrewhossam.opengeomock.domain

import com.andrewhossam.opengeomock.data.SearchRepository
import org.koin.core.annotation.Factory

@Factory
class SaveRecentSearchUseCase(
    private val searchRepository: SearchRepository,
) {
    suspend operator fun invoke(query: String) = searchRepository.saveSearch(query)
}
