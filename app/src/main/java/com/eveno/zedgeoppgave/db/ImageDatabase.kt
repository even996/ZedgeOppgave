package com.eveno.zedgeoppgave.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eveno.zedgeoppgave.models.Hit

@Database(
    entities = [Hit::class],
    version = 1
)
abstract class ImageDatabase : RoomDatabase(){

    abstract fun getImageDao(): ImageDao

    companion object{
        @Volatile
        private var instance: ImageDatabase? = null
        private  val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDataBase(context).also { instance = it }
        }

        private fun createDataBase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                ImageDatabase::class.java,
                "image_db.db"
            ).build()
    }
}