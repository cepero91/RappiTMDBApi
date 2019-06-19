package com.josancamon19.rappimoviedatabaseapi.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.josancamon19.rappimoviedatabaseapi.data.database.GenreDao


class DetailsViewModelFactory(
    private val genresDao:GenreDao,
    private val genresId: List<Int>,
    private val movieId: Long
) :
    ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(genresDao, genresId, movieId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}