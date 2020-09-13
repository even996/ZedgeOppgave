package com.eveno.zedgeoppgave.repository

import com.eveno.zedgeoppgave.api.RetrofitInstance
import com.eveno.zedgeoppgave.api.RetrofitInstance.Companion.api
import com.eveno.zedgeoppgave.db.ImageDatabase
import retrofit2.Retrofit

class ImageRepository(
    val db: ImageDatabase
) {
    suspend fun getNewImages(page : Int) =
        RetrofitInstance.api.getNewImages(page)
}