package com.andrewhossam.opengeomock.domain

import com.andrewhossam.opengeomock.data.SearchRepository
import org.koin.core.annotation.Factory

@Factory
class GetRecentSearchUseCase(
    private val searchRepository: SearchRepository,
) {
    operator fun invoke() = searchRepository.getRecentSearches()
}
