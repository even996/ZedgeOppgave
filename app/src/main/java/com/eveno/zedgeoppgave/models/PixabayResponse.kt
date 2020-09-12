package com.eveno.zedgeoppgave.models

import com.eveno.zedgeoppgave.models.Image

data class PixabayResponse(
    val hits: List<Image>,
    val total: Int,
    val totalHits: Int
)