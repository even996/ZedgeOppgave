package com.eveno.zedgeoppgave.models

import com.eveno.zedgeoppgave.models.Hit

data class ImageResponse(
    // hit er da bildene
    val hits: MutableList<Hit>,
    val total: Int,
    val totalHits: Int
)