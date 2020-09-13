package com.eveno.zedgeoppgave.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eveno.zedgeoppgave.models.ImageResponse
import com.eveno.zedgeoppgave.repository.ImageRepository
import com.eveno.zedgeoppgave.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ImageViewModel(
    val imageRepository: ImageRepository
) : ViewModel() {

    val newImages: MutableLiveData<Resource<ImageResponse>> = MutableLiveData()
    var newImagesPage = 1

    init {
        getNewImages()
    }

    fun getNewImages() = viewModelScope.launch {
        newImages.postValue(Resource.Loading())
        val response = imageRepository.getNewImages(newImagesPage)
        newImages.postValue(handleNewImagesResponse(response))


    }

    private fun handleNewImagesResponse(response : Response<ImageResponse>) : Resource<ImageResponse> {
        if(response.isSuccessful){
            response.body()?.let {resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}