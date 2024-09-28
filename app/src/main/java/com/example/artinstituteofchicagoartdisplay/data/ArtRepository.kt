package com.example.artinstituteofchicagoartdisplay.data

import com.example.artinstituteofchicagoartdisplay.data.network.ArtApiService
import com.example.artinstituteofchicagoartdisplay.domain.model.ArtPhoto

interface ArtRepository {
    suspend fun getArtPhotos(): ArtPhoto
}

/**
 * Network Implementation of Repository that fetch art photos list from artApi.
 */
class NetworkArtRepository(
    private val artApiService: ArtApiService
) : ArtRepository {
    /** Fetches list of Art Photos from artic.edu/api*/
    override suspend fun getArtPhotos(): ArtPhoto = artApiService.getPhotos()
}
