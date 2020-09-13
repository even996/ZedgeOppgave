package com.eveno.zedgeoppgave.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.eveno.zedgeoppgave.models.Hit

@Dao
interface ImageDao {
    // hit er bildene

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(hit: Hit): Long

    @Query("select * from images")
    fun getAllImages(): LiveData<List<Hit>>

    @Delete
    suspend fun deleteImage(hit: Hit)

}