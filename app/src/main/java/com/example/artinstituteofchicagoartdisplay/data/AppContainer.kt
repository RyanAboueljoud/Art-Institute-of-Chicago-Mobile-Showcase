package com.example.artinstituteofchicagoartdisplay.data

import com.example.artinstituteofchicagoartdisplay.data.network.ArtApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val artRepository: ArtRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.artic.edu/api/v1/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: ArtApiService by lazy {
        retrofit.create(ArtApiService::class.java)
    }

    /**
     * DI implementation for art photos repository
     */
    override val artRepository: ArtRepository by lazy {
        NetworkArtRepository(retrofitService)
    }
}