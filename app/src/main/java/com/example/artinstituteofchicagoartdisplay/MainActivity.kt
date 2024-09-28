package com.example.artinstituteofchicagoartdisplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.artinstituteofchicagoartdisplay.ui.ArtPhotoApp
import com.example.artinstituteofchicagoartdisplay.ui.theme.ArtInstituteOfChicagoArtDisplayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtInstituteOfChicagoArtDisplayTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtPhotoApp()
                }
            }
        }
    }
}
