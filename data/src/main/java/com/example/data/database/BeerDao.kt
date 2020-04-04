package com.example.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BeerDao {

    @Query("SELECT * FROM beer_table")
    fun getAllBeers() : DataSource.Factory<Int, DatabaseBeer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg beers: DatabaseBeer)

    @Query("SELECT * FROM beer_table WHERE id = :beerId")
    suspend fun getBeer(beerId: Long) : DatabaseBeer?

    @Query("DELETE FROM beer_table")
    fun deleteAll()
}