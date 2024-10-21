package com.example.artinstituteofchicagoartdisplay.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.artinstituteofchicagoartdisplay.R
import com.example.artinstituteofchicagoartdisplay.domain.model.ArtApiData
import com.example.artinstituteofchicagoartdisplay.ui.theme.ArtInstituteOfChicagoArtDisplayTheme

@Composable
fun HomeScreen(
    artUiState: ArtUiState,
    artViewModel: HomeViewModel,
    retryAction: () -> Unit,    // TODO - Enable Retry button is connection to server cannot be established
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    Column {
        when (artUiState) {
            is ArtUiState.Loading -> LoadingScreen(modifier = Modifier.padding(contentPadding))
            is ArtUiState.Success -> ArtGrid(
                modifier = modifier.weight(1f),
                photos =  artUiState.photos.data,
                iiifUrl =  artUiState.photos.config.iiifUrl,
                contentPadding = contentPadding
            )

            is ArtUiState.Error -> LoadingScreen()
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
        ){
            ElevatedButton(
                onClick = {
                    when (artUiState) {
                        is ArtUiState.Success -> {
                            artViewModel.prevPage()
                        }
                        else -> {}
                    }
                },
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Previous Page"
                )
                Text(
                    text = "Previous",
                    modifier = modifier.padding(start = 4.dp)
                )
            }
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(artViewModel.currPage.toString())
                    }
                    append("...")
                    when(artUiState) {
                        is ArtUiState.Success -> append(artUiState.photos.pagination.totalPages.toString())
                        else -> {}
                    }
                }
            )
            ElevatedButton(
                onClick = {
                    when (artUiState) {
                        is ArtUiState.Success -> {
                            artViewModel.nextPage()
                        }
                        else -> {}
                    }
                },
            ) {
                Text(
                    text = "Next",
                    modifier = modifier.padding(end = 4.dp)
                )
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next Page"
                )
            }
        }
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
    modifier: Modifier = Modifier,
    photos: List<ArtApiData>,
    iiifUrl: String,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = photos, key = { photo: ArtApiData -> photo.id }) { photo ->
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
    val imgSource = "$iiifUrl/${photo.imageId}/full/843,/0/default.jpg"

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
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
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
        ArtGrid(photos = mockData, iiifUrl = "")
    }
}