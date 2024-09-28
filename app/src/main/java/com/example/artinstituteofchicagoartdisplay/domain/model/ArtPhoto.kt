package com.example.artinstituteofchicagoartdisplay.domain.model

import com.google.gson.annotations.SerializedName

data class Pagination (
    val total: Int,
    val limit: Int,
    val offset: Int,
    @SerializedName(value = "total_pages")
    val totalPages: Int,
    @SerializedName(value = "current_page")
    val currentPage: Int,
    @SerializedName(value = "next_url")
    val nextUrl: String
)

data class ArtApiData (
    val id: Int,
    val title: String,
    @SerializedName(value = "image_id")
    val imageId: String,
    @SerializedName(value = "artist_display")
    val artist: String,
    @SerializedName(value = "alt_image_id")
    val altImageId: List<String>
)

data class ArtApiConfig (
    @SerializedName(value = "iiif_url")
    val iiifUrl: String,
    @SerializedName(value = "website_url")
    val websiteUrl: String
)

data class ArtPhoto (
    val pagination: Pagination,
    val data: List<ArtApiData>,
    val config: ArtApiConfig
)