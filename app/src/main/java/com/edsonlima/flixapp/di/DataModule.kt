package com.edsonlima.flixapp.di

import com.edsonlima.flixapp.BuildConfig
import com.edsonlima.flixapp.data.api.ServiceApi
import com.edsonlima.flixapp.httpInterceptor.QueryParameterInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providersQueryParameterInterceptor(): QueryParameterInterceptor {
        return QueryParameterInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        queryParameterInterceptor: QueryParameterInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(queryParameterInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }

    @Provides
    @Singleton
    fun providersServiceApiProvider(
        retrofit: Retrofit
    ): ServiceApi {
        return retrofit.create(ServiceApi::class.java)
    }

}