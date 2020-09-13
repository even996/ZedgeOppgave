package com.eveno.zedgeoppgave.api

import com.eveno.zedgeoppgave.models.ImageResponse
import com.eveno.zedgeoppgave.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageAPI {
    @GET("/api/")
    suspend fun getNewImages(
        @Query("page")
        pageNumber: Int =1,
        @Query("key")
        apiKey : String = API_KEY
    ): Response<ImageResponse>


    @GET("/api/")
    suspend fun searchImages(
        @Query("q")
        searchQuery : String,
        @Query("page")
        pageNumber: Int =1,
        @Query("key")
        apiKey : String = API_KEY
    ): Response<ImageResponse>
}