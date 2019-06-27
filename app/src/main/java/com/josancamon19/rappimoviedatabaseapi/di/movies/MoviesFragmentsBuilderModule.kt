package com.josancamon19.rappimoviedatabaseapi.di.movies

import com.josancamon19.rappimoviedatabaseapi.movies.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MoviesFragmentsBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeMoviesFragment(): MoviesFragment
}