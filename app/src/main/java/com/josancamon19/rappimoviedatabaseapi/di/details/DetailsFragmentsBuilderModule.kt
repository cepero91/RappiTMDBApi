package com.josancamon19.rappimoviedatabaseapi.di.details

import com.josancamon19.rappimoviedatabaseapi.details.DetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailsFragmentsBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment(): DetailsFragment
}