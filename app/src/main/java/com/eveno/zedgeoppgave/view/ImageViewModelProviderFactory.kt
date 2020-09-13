package com.eveno.zedgeoppgave.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eveno.zedgeoppgave.repository.ImageRepository

class ImageViewModelProviderFactory(
    val imageRepository: ImageRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageViewModel(imageRepository) as T
    }
}