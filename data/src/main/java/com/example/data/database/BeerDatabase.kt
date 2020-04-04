package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseBeer::class, FavouriteEntity::class], version = 6, exportSchema = false)
abstract class BeerDatabase : RoomDatabase(){

    abstract val beerDao : BeerDao
    abstract val favDao : FavouritesDao

    companion object {

        @Volatile
        private var INSTANCE: BeerDatabase? = null

        fun getDatabase(context: Context): BeerDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BeerDatabase::class.java,
                    "word_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}