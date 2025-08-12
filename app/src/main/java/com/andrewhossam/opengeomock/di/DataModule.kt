package com.andrewhossam.opengeomock.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    includes = [
        DispatchersModule::class,
        JsonModule::class,
        NetworkModule::class,
        StorageModule::class,
    ],
)
@ComponentScan
class DataModule
