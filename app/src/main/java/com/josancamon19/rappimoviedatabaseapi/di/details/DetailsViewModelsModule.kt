package com.josancamon19.rappimoviedatabaseapi.di.details

import androidx.lifecycle.ViewModel
import com.josancamon19.rappimoviedatabaseapi.details.DetailsViewModel
import com.josancamon19.rappimoviedatabaseapi.di.viewmodel.ViewModelKey
import com.josancamon19.rappimoviedatabaseapi.movies.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailsViewModelsModule {
// DetailViewModel will not be injected cause we already have a provider
}