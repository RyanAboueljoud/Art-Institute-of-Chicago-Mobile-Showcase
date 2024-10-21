package com.example.artinstituteofchicagoartdisplay.data.network

import com.example.artinstituteofchicagoartdisplay.domain.model.ArtPhoto
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtApiService {
    @GET("artworks?fields=id,title,image_id,artist_display,artist,alt_image_ids")
    suspend fun getPhotos(@Query("page") page: String): ArtPhoto
}