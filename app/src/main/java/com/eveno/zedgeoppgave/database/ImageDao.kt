package com.eveno.zedgehjemmeoppgave.db



import androidx.lifecycle.LiveData
import androidx.room.*
import com.eveno.zedgeoppgave.models.Image

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(image: Image): Long

    @Query("SELECT * FROM images")
    fun getAllImages(): LiveData<List<Image>>

    @Delete
    suspend fun deleteImage(image: Image)
}