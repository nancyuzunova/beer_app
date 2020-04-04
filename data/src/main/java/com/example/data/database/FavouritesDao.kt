package com.example.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouritesDao {

    @Query("SELECT * FROM favourite_beers WHERE owner_id = :id")
    fun getAllFavouriteBeers(id: String): LiveData<List<FavouriteEntity>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertBeer(favouriteBeer: FavouriteEntity)

    @Query("SELECT COUNT(id) FROM favourite_beers WHERE owner_id = :id")
    suspend fun getNumberOfFavouriteBeers(id: String): Int

    @Query("DELETE FROM favourite_beers WHERE id = :id AND owner_id = :userId")
    fun deleteBeer(id: Long, userId: String)

    @Query("SELECT * FROM favourite_beers WHERE id = :beerId AND owner_id = :userId")
    suspend fun getBeerById(beerId: Long, userId: String): FavouriteEntity?

    @Query("SELECT * FROM favourite_beers WHERE owner_id = :userId ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomBeer(userId: String): FavouriteEntity?
}