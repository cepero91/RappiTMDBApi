package com.josancamon19.rappimoviedatabaseapi.di.app

import com.josancamon19.rappimoviedatabaseapi.MainActivity
import com.josancamon19.rappimoviedatabaseapi.di.details.DetailsFragmentsBuilderModule
import com.josancamon19.rappimoviedatabaseapi.di.details.DetailsModule
import com.josancamon19.rappimoviedatabaseapi.di.details.DetailsViewModelsModule
import com.josancamon19.rappimoviedatabaseapi.di.movies.MoviesFragmentsBuilderModule
import com.josancamon19.rappimoviedatabaseapi.di.movies.MoviesModule
import com.josancamon19.rappimoviedatabaseapi.di.movies.MoviesScope
import com.josancamon19.rappimoviedatabaseapi.di.movies.MoviesViewModelsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilderModule {

    @MoviesScope
    @ContributesAndroidInjector(
        modules = [
            MoviesModule::class,
            MoviesFragmentsBuilderModule::class,
            MoviesViewModelsModule::class,
            DetailsFragmentsBuilderModule::class,
            DetailsViewModelsModule::class,
            DetailsModule::class
        ]
    )
    abstract fun contributeMainActivity() : MainActivity
}