package com.josancamon19.rappimoviedatabaseapi.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.josancamon19.rappimoviedatabaseapi.data.models.Genre

@Dao
interface GenreDao {

    @Query("SELECT * FROM genres")
    fun loadGenres(): List<Genre>

    @Insert
    fun insertGenres(genres: List<Genre>)

    @Query("DELETE FROM genres")
    fun deleteAll()
}