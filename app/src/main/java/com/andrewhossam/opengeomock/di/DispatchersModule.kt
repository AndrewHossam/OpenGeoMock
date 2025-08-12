package com.andrewhossam.opengeomock.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan
class DispatchersModule {
    @Single
    fun dispatcher() = Dispatchers.IO
}
