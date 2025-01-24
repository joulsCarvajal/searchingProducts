package com.example.searchingproducts.di

import com.example.searchingproducts.data.remote.api.MercadoLibreApi
import com.example.searchingproducts.data.remote.api.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApi(): MercadoLibreApi = RetrofitClient.api
}