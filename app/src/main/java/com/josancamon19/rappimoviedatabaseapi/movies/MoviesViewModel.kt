package com.josancamon19.rappimoviedatabaseapi.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.josancamon19.rappimoviedatabaseapi.data.models.Movie
import timber.log.Timber


class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    var repository: MoviesRepository = MoviesRepository(application)

    init {
        Timber.d("MoviesViewModel Initiated")
    }

    fun getMovies(): LiveData<List<Movie>> {
        return repository.movies
    }

    fun setCategory(category: String) {
        repository.setCategorySelected(category)
    }

    fun queryMovies(query: String) {
        repository.queryMovies(query)
    }

    fun clearQuery(){
        repository.restoreMovies()
    }

    override fun onCleared() {
        super.onCleared()
        repository.clear()
    }


}