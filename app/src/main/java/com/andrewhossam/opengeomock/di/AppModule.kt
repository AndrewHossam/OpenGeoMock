package com.andrewhossam.opengeomock.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DataModule::class])
@ComponentScan("com.andrewhossam.opengeomock")
class AppModule
