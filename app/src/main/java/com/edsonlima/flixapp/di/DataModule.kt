package com.edsonlima.flixapp.di

import com.edsonlima.flixapp.data.api.ServiceApi
import com.edsonlima.flixapp.service.ServiceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providersServiceProvider() = ServiceProvider()

    @Provides
    @Singleton
    fun providersServiceApiProvider(
        serviceProvider: ServiceProvider
    ): ServiceApi {
        return serviceProvider.serviceCreate(ServiceApi::class.java)
    }

}