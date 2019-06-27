package com.josancamon19.rappimoviedatabaseapi.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.josancamon19.rappimoviedatabaseapi.data.database.GenreDao
import com.josancamon19.rappimoviedatabaseapi.data.models.Genre
import com.josancamon19.rappimoviedatabaseapi.data.models.Video
import com.josancamon19.rappimoviedatabaseapi.data.network.MoviesApi

class DetailsViewModel(genresDao: GenreDao, genresId: List<Int>, movieId: Long, moviesApi: MoviesApi) : ViewModel() {

    private val repository: DetailsRepository = DetailsRepository(genresDao, genresId, movieId, moviesApi)

    fun getMovieGenres(): LiveData<MutableList<Genre>> {
        return repository.movieGenres
    }

    fun getVideos(): LiveData<List<Video>> {
        return repository.videos
    }

}