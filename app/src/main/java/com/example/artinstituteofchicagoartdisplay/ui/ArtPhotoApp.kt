package com.example.artinstituteofchicagoartdisplay.ui

import android.util.Log
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.artinstituteofchicagoartdisplay.ui.screens.home.HomeScreen
import com.example.artinstituteofchicagoartdisplay.ui.screens.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtPhotoApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { ArtTopBar(scrollBehavior) },
    ) { innerPadding ->
        Surface (
        ) {
            val artViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
            HomeScreen(
                artUiState = artViewModel.artUiState,
                artViewModel = artViewModel,
                retryAction = {},
                contentPadding = innerPadding
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtTopBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Art Institute of Chicago",
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        navigationIcon = {
            IconButton(onClick = { Log.d( "MAIN ACTIVITY", "Clicked!")}) { }
        },
        colors = topAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White
        ),
        modifier = modifier
    )
}

