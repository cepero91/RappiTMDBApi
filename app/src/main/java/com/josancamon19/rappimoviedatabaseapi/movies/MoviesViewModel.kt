package com.josancamon19.rappimoviedatabaseapi.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.josancamon19.rappimoviedatabaseapi.data.models.Movie
import timber.log.Timber
import javax.inject.Inject


class MoviesViewModel @Inject constructor(private val repository:  MoviesRepository) : ViewModel() {

    private var movies : LiveData<List<Movie>> = repository.movies

    fun getMovies(): LiveData<List<Movie>> {
        return movies
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