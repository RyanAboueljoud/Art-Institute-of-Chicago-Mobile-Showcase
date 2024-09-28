package com.example.artinstituteofchicagoartdisplay.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import com.example.artinstituteofchicagoartdisplay.ArtPhotoApplication
import com.example.artinstituteofchicagoartdisplay.data.ArtRepository
import com.example.artinstituteofchicagoartdisplay.domain.model.ArtPhoto
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface ArtUiState {
    data class Success(val photos: ArtPhoto) : ArtUiState
    object Error : ArtUiState
    object Loading : ArtUiState
}


class HomeViewModel(private val artRepository: ArtRepository) : ViewModel() {
    var artUiState: ArtUiState by mutableStateOf(ArtUiState.Loading)
        private set

    init {
        getArtPhotos()
    }

    fun getArtPhotos() {
        viewModelScope.launch {
            artUiState = ArtUiState.Loading
            artUiState = try {
                ArtUiState.Success(artRepository.getArtPhotos())
            } catch (e: IOException) {
                ArtUiState.Error
            }  catch (e: HttpException) {
                ArtUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ArtPhotoApplication)
                val artRepository = application.container.artRepository
                HomeViewModel(artRepository = artRepository)
            }
        }
    }

}