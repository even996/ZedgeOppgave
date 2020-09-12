package com.eveno.zedgeoppgave.api

import com.eveno.zedgehjemmeoppgave.util.Constants.Companion.API_KEY
import com.eveno.zedgeoppgave.models.PixabayResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesAPI {
    @GET()
    suspend fun getImages(
        @Query("page")
        pageNumber: Int = 1,
        @Query("key")
        apiKey: String = API_KEY
    ): Response<PixabayResponse>

    //https://pixabay.com/api/?key=18276471-4f6103f36f94ebd843e6cbf4f&q=yellow+flowers&image_type=photo&pretty=true

    @GET()
    suspend fun searchForImages(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("key")
        apiKey: String = API_KEY
    ): Response<PixabayResponse>
}