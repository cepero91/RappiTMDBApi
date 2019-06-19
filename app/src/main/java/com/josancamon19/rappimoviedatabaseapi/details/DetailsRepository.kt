package com.josancamon19.rappimoviedatabaseapi.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.josancamon19.rappimoviedatabaseapi.data.database.GenreDao
import com.josancamon19.rappimoviedatabaseapi.data.models.Genre
import com.josancamon19.rappimoviedatabaseapi.data.models.Video
import com.josancamon19.rappimoviedatabaseapi.data.network.MoviesApi
import kotlinx.coroutines.*
import timber.log.Timber

class DetailsRepository(private val genresDao: GenreDao, private val genresId: List<Int>, private val movieId: Long){

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _movieGenres = MutableLiveData<MutableList<Genre>>()
    val movieGenres: LiveData<MutableList<Genre>>
        get() = _movieGenres

    private val _allGenres = MutableLiveData<List<Genre>>()

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>>
        get() = _videos

    init {
        loadGenres()
        loadVideosFromId()
    }

    private fun loadGenres() {
        uiScope.launch {
            _allGenres.value = loadGenresFromDb()
            setMovieGenres(genresId)
        }
    }

    private suspend fun loadGenresFromDb(): List<Genre>? {
        return withContext(Dispatchers.IO) {
            genresDao.loadGenres()
        }
    }

    private fun setMovieGenres(movieGenresIds: List<Int>) {
        val genres: MutableList<Genre> = mutableListOf()
        movieGenresIds.forEach {
            val itemsFound = _allGenres.value?.filter { genre -> genre.id == it }
            genres.add(itemsFound?.get(0)!!)
        }
        _movieGenres.value = genres
    }

    private fun loadVideosFromId() {
        uiScope.launch {
            val videoResponse = MoviesApi.retrofitService.getMovieVideosAsync(movieId)
            try {
                val videos = videoResponse.await().videos
                _videos.value = videos
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}