package com.example.artinstituteofchicagoartdisplay

import android.app.Application
import com.example.artinstituteofchicagoartdisplay.data.AppContainer
import com.example.artinstituteofchicagoartdisplay.data.DefaultAppContainer

class ArtPhotoApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}