package com.josancamon19.rappimoviedatabaseapi.movies

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.josancamon19.rappimoviedatabaseapi.data.database.AppDatabase
import com.josancamon19.rappimoviedatabaseapi.data.models.Genre
import com.josancamon19.rappimoviedatabaseapi.data.models.Movie
import com.josancamon19.rappimoviedatabaseapi.data.network.MoviesApi
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import com.soywiz.klock.days
import com.soywiz.klock.parse
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    application: Application,
    private val db: AppDatabase,
    private val moviesApi: MoviesApi
) {

    companion object {
        private const val KEY_DATE_UPDATED: String = "key_date_updated"
        private const val DATE_UPDATED_DEFAULT: String = ""
    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val prefs = application.getSharedPreferences("genres", Context.MODE_PRIVATE)!!
    private val connectivityManager = application
        .applicationContext
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private fun verifyAvailableNetwork(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    private val _movies = MediatorLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies


    private val _categorySelected = MutableLiveData<String>()
    val categorySelected: LiveData<String>
        get() = _categorySelected


    init {
        initMovies()
    }

    private fun initMovies() {
        uiScope.launch {
            if (verifyAvailableNetwork()) {
                val lastDateUpdated = prefs.getString(KEY_DATE_UPDATED, DATE_UPDATED_DEFAULT)
                if (lastUpdateExpired(lastDateUpdated!!)) {
                    clearDatabase()
                    loadMoviesFromNetwork("popular")
                    loadMovies("popular")
                    loadMoviesFromNetwork("top_rated")
                    loadMoviesFromNetwork("upcoming")
                    loadGenres()
                    //Timber.d("Loaded from network, data have expired")
                    with(prefs.edit()) {
                        putString(KEY_DATE_UPDATED, DateTime.now().local.toString())
                        apply()
                        //Timber.d("Update date updated.")
                    }
                } else {
                    loadMovies("popular")
                }

            } else {
                loadMovies("popular")
            }
        }
    }

    private fun lastUpdateExpired(dateString: String): Boolean {
        if (dateString.isEmpty() || dateString == Companion.DATE_UPDATED_DEFAULT) return true
        val dateFormat = DateFormat("EEE, dd MMM yyyy HH:mm:ss z")
        val lastDateUpdated = dateFormat.parse(dateString)
        val expirationDate = (lastDateUpdated + 7.days)
        val now = DateTime.now().local
        return (now > expirationDate)
    }

    private suspend fun clearDatabase() {
        withContext(Dispatchers.IO) {
            db.moviesDao.deleteAll()
        }
    }

    private suspend fun loadMoviesFromNetwork(category: String) {
        val response = moviesApi.getMoviesAsync(category)
        try {
            val moviesResponse = response.await()
            val movies = moviesResponse.movies
            movies.forEach { movie -> movie.category = category }
            saveMoviesResponse(movies)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private suspend fun saveMoviesResponse(movies: List<Movie>) {
        withContext(Dispatchers.IO) {
            db.moviesDao.insertMovies(movies)
        }
    }


    private fun loadMovies(category: String) {
        uiScope.launch {
            val newMovies = loadMoviesFromDatabase(category)
            _movies.addSource(newMovies) {
                _movies.value = it
            }
        }
    }

    private suspend fun loadMoviesFromDatabase(category: String): LiveData<List<Movie>> {
        return withContext(Dispatchers.IO) {
            db.moviesDao.getMoviesFromCategory(category)
        }
    }

    fun setCategorySelected(category: String) {
        _categorySelected.value = category
        updateDataFromCategorySelected(category)
    }

    private fun updateDataFromCategorySelected(category: String) {
        when (category) {
            "Popular Movies" -> loadMovies("popular")
            "Top Rated Movies" -> loadMovies("top_rated")
            "Upcoming Movies" -> loadMovies("upcoming")
        }
    }

    private fun loadGenres() {
        uiScope.launch {
            val genres = moviesApi.getGenresAsync()
            try {
                val genresList = genres.await().genres
                saveGenres(genresList)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private suspend fun saveGenres(genres: List<Genre>) {
        withContext(Dispatchers.IO) {
            db.genresDao.deleteAll()
            db.genresDao.insertGenres(genres)
        }
    }

    fun queryMovies(query: String) {
        if (query.isEmpty()) {
            restoreMovies()
            return
        }
        uiScope.launch {
            val queryResult = loadFromDatabaseQuery(query)
            _movies.addSource(queryResult) {
                _movies.value = it
                _movies.removeSource(queryResult)
            }
        }
    }

    private suspend fun loadFromDatabaseQuery(query: String): LiveData<List<Movie>> {
        return withContext(Dispatchers.IO) {
            val category = when (_categorySelected.value!!) {
                "Popular Movies" -> "popular"
                "Top Rated Movies" -> "top_rated"
                "Upcoming Movies" -> "upcoming"
                else -> "popular"
            }
            db.moviesDao.queryTitle("%$query%", category)
        }
    }

    fun restoreMovies() {
        uiScope.launch {
            val category = when (_categorySelected.value!!) {
                "Popular Movies" -> "popular"
                "Top Rated Movies" -> "top_rated"
                "Upcoming Movies" -> "upcoming"
                else -> "popular"
            }
            loadMovies(category)
        }
    }

    fun clear() {
        viewModelJob.cancel()
    }


}