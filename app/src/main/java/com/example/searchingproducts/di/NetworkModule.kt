package com.example.searchingproducts.di

import android.content.Context
import com.example.searchingproducts.data.remote.api.MercadoLibreApi
import com.example.searchingproducts.data.repository.ProductRepository
import com.example.searchingproducts.ui.search.SearchPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApi(): MercadoLibreApi =
        Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MercadoLibreApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(api: MercadoLibreApi): ProductRepository =
        ProductRepository(api)

    @Provides
    @Singleton
    fun provideSearchPreferences(@ApplicationContext context: Context): SearchPreferences {
        return SearchPreferences(context)
    }

}