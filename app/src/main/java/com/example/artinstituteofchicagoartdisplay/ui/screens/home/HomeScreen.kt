package com.example.artinstituteofchicagoartdisplay.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.artinstituteofchicagoartdisplay.domain.model.ArtApiData
import com.example.artinstituteofchicagoartdisplay.ui.theme.ArtInstituteOfChicagoArtDisplayTheme

@Composable
fun HomeScreen(
    artUiState: ArtUiState,
    retryAction: () -> Unit,    // TODO - Enable Retry button is connection to server cannot be established
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    when (artUiState) {
        is ArtUiState.Loading -> LoadingScreen(modifier = Modifier.padding(contentPadding))
        is ArtUiState.Success -> ArtGrid(artUiState.photos.data, artUiState.photos.config.iiifUrl, contentPadding, modifier)
        is ArtUiState.Error -> LoadingScreen()
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box (
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(128.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
fun ArtGrid(
    photos: List<ArtApiData>,
    iiifUrl: String,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding
    ) {
        items(items = photos, key = { photo: ArtApiData -> photo.id}){ photo ->
            Thumbnail(
                photo = photo,
                iiifUrl = iiifUrl,
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )
        }
    }
}

@Composable
fun Photo (photo: ArtApiData, iiifUrl: String, modifier: Modifier = Modifier) {
    var imgSource: String = "$iiifUrl/${photo.imageId}/full/843,/0/default.jpg"

    Column (
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(imgSource)
                .crossfade(true).build(),
            contentDescription = photo.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun Thumbnail (photo: ArtApiData, iiifUrl: String, modifier: Modifier = Modifier) {
    val thumbnailSrc = "$iiifUrl/${photo.imageId}/full/200,/0/default.jpg"

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = CardDefaults.shape,
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(thumbnailSrc)
                .crossfade(true)
                .build(),
            contentDescription = photo.title,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun HomeScreenPreivew() {
    ArtInstituteOfChicagoArtDisplayTheme {
        val mockData = List(10) {
            ArtApiData(
                id = it,
                title = "",
                imageId = "$it",
                artist = "",
                altImageId = listOf("")
            )
        }
        ArtGrid(mockData, "")
    }
}