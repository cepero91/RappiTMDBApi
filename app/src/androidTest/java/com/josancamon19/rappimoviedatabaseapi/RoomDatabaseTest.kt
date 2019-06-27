package com.josancamon19.rappimoviedatabaseapi

import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.josancamon19.rappimoviedatabaseapi.data.database.AppDatabase
import com.josancamon19.rappimoviedatabaseapi.data.database.MovieDao
import com.josancamon19.rappimoviedatabaseapi.data.models.Movie
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest {
    private lateinit var moviesDao: MovieDao
    private lateinit var db: AppDatabase

    private val testMovie1 = Movie(
        123, 182, "Avengers", "Overview Movie", "en",
        213.98, 9.4, 100, "", false, listOf(1, 2), false,
        "10-10-2000", "popular"
    )
    private val testMovie2 = testMovie1.copy(databaseId = 111, title = "Infinity")
    private val testMovie3 = testMovie1.copy(databaseId = 112, title = "End game")

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        moviesDao = db.moviesDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetMovie() {
        moviesDao.insertMovie(testMovie1)
        val movies = moviesDao.getMovies()
        assertThat(movies.last(), equalTo(testMovie1))
    }

    @Test
    @Throws(Exception::class)
    fun insertListMovies() {
        val movies = listOf(testMovie1, testMovie2, testMovie3)

        moviesDao.insertMovies(movies)

        val moviesRetrieved = moviesDao.getMovies()
        assertEquals(moviesRetrieved.size, movies.size)
    }

    @Test
    @Throws(Exception::class)
    fun loadMoviesFromCategory() {
        val movies = listOf(testMovie1, testMovie2.copy(category = "top_rated"), testMovie3.copy(category = "upcoming"))

        moviesDao.insertMovies(movies)

        val moviesRetrieved = moviesDao.getMoviesFromCategory("popular")
       // assertTrue(moviesRetrieved[0] == testMovie1)
    }

    @Test
    @Throws(Exception::class)
    fun queryMovies() {
        val movies = listOf(testMovie1, testMovie2, testMovie3)

        moviesDao.insertMovies(movies)

        val moviesRetrieved = moviesDao.queryTitle("Avengers", "popular")
      //  assertTrue(moviesRetrieved.contains(testMovie1))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllMovies() {
        moviesDao.insertMovie(testMovie1)
        moviesDao.deleteAll()
        val movies = moviesDao.getMovies()
        assertTrue(movies.isEmpty())
    }
}