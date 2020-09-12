package com.eveno.zedgehjemmeoppgave.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eveno.zedgeoppgave.models.Image

@Database(
    entities = [Image::class],
    version = 1
)


abstract class ImageDatabase : RoomDatabase() {

    abstract fun getImageDao(): ImageDao

    companion object{
        @Volatile
        private var instance: ImageDatabase? = null
        private val LOCK = Any()

        // kjører når man lager  databasen og at det ikke kan være flere databaser.
        // Singleton
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also { instance = it}
        }


        private fun createDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext, ImageDatabase:: class.java,
            "image_dbtabase.db").build()
    }
}