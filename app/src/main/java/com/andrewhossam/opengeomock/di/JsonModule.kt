package com.andrewhossam.opengeomock.di

import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan
class JsonModule {
    @Single
    fun provideJson() =
        Json {
            ignoreUnknownKeys = true
            prettyPrint = false
            isLenient = true
        }
}
