package com.josancamon19.rappimoviedatabaseapi.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.josancamon19.rappimoviedatabaseapi.data.database.GenreDao
import com.josancamon19.rappimoviedatabaseapi.data.models.Genre
import com.josancamon19.rappimoviedatabaseapi.data.models.Video

class DetailsViewModel(genresDao: GenreDao, genresId: List<Int>, movieId: Long) : ViewModel() {

    private val repository: DetailsRepository = DetailsRepository(genresDao, genresId, movieId)

    fun getMovieGenres(): LiveData<MutableList<Genre>> {
        return repository.movieGenres
    }

    fun getVideos(): LiveData<List<Video>> {
        return repository.videos
    }

}