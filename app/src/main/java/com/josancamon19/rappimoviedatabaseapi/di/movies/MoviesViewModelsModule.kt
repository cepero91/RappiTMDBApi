package com.josancamon19.rappimoviedatabaseapi.di.movies

import androidx.lifecycle.ViewModel
import com.josancamon19.rappimoviedatabaseapi.di.viewmodel.ViewModelKey
import com.josancamon19.rappimoviedatabaseapi.movies.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MoviesViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun bindsMoviesViewModel(moviesViewModel: MoviesViewModel): ViewModel
}