package com.eveno.zedgeoppgave.repository

import com.eveno.zedgeoppgave.api.RetrofitInstance
import com.eveno.zedgeoppgave.api.RetrofitInstance.Companion.api
import com.eveno.zedgeoppgave.db.ImageDatabase
import com.eveno.zedgeoppgave.models.Hit
import retrofit2.Retrofit

class ImageRepository(
    val db: ImageDatabase
) {
    suspend fun getNewImages(page : Int) =
        RetrofitInstance.api.getNewImages(page)

    suspend fun searchImage(searchQuery: String, pageNumer: Int) =
        RetrofitInstance.api.searchImages(searchQuery,pageNumer)

    suspend fun upsert(hit: Hit) = db.getImageDao().upsert(hit)

    fun getSavedImages() = db.getImageDao().getAllImages()

    suspend fun deleteImage(hit: Hit) = db.getImageDao().deleteImage(hit)


}