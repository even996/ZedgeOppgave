package com.eveno.zedgeoppgave.view

import android.media.Image
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eveno.zedgeoppgave.models.Hit
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
    var newImagesResponse : ImageResponse? = null


    val searchImages: MutableLiveData<Resource<ImageResponse>> = MutableLiveData()
    var searchImagePage = 1
    var searchImagesResponse : ImageResponse? = null




    init {
        getNewImages()
    }

    fun getNewImages() = viewModelScope.launch {
        newImages.postValue(Resource.Loading())
        val response = imageRepository.getNewImages(newImagesPage)
        newImages.postValue(handleNewImagesResponse(response))


    }

    fun searchImages(searchQuery: String) = viewModelScope.launch {
        searchImages.postValue(Resource.Loading())
        val response = imageRepository.searchImage(searchQuery, searchImagePage)
        searchImages.postValue(handleSearchImagesResponse(response))
    }

    private fun handleNewImagesResponse(response : Response<ImageResponse>) : Resource<ImageResponse> {
        if(response.isSuccessful){
            response.body()?.let {resultResponse ->
                newImagesPage++
                if(newImagesResponse == null){
                    newImagesResponse = resultResponse
                } else {
                    val oldImages = newImagesResponse?.hits
                    val newImages = resultResponse.hits
                    oldImages?.addAll(newImages)
                }
                return Resource.Success(newImagesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private fun handleSearchImagesResponse(response : Response<ImageResponse>) : Resource<ImageResponse> {
        if(response.isSuccessful){
            response.body()?.let {resultResponse ->
                searchImagePage++
                if(searchImagesResponse == null){
                    searchImagesResponse = resultResponse
                } else {
                    val oldImages = searchImagesResponse?.hits
                    val newImages = resultResponse.hits
                    oldImages?.addAll(newImages)
                }
                return Resource.Success(searchImagesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun saveImage(hit: Hit) = viewModelScope.launch {
        imageRepository.upsert(hit)
    }

    fun getSavedImages() = imageRepository.getSavedImages()

    fun deleteImage(hit: Hit) = viewModelScope.launch {
        imageRepository.deleteImage(hit)
    }

}









