package com.josancamon19.rappimoviedatabaseapi.di.movies

import android.app.Application
import com.josancamon19.rappimoviedatabaseapi.data.database.AppDatabase
import com.josancamon19.rappimoviedatabaseapi.data.network.MoviesApi
import com.josancamon19.rappimoviedatabaseapi.movies.MoviesRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MoviesModule {

    @MoviesScope
    @Provides
    fun provideMoviesRepository(
        application: Application,
        database: AppDatabase,
        moviesApi: MoviesApi
    ): MoviesRepository {
        return MoviesRepository(application, database, moviesApi)
    }

    @MoviesScope
    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return AppDatabase.getInstance(application.applicationContext)
    }

    @MoviesScope
    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
        val retrofitService: MoviesApi by lazy { retrofit.create(MoviesApi::class.java) }
        return retrofitService
    }
}