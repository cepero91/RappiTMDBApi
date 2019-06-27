package com.josancamon19.rappimoviedatabaseapi.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.josancamon19.rappimoviedatabaseapi.data.models.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getMovies(): List<Movie>

    @Query("SELECT * FROM movies WHERE category=:category")
    fun getMoviesFromCategory(category: String): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE original_title LIKE :query AND category=:category")
    fun queryTitle(query: String, category:String) : LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Insert
    fun insertMovie(movie: Movie)

    @Query("DELETE FROM movies")
    fun deleteAll()

}