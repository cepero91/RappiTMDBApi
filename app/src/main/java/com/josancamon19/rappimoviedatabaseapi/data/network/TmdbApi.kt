package com.josancamon19.rappimoviedatabaseapi.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.josancamon19.rappimoviedatabaseapi.data.models.GenresResponse
import com.josancamon19.rappimoviedatabaseapi.data.models.MoviesResponse
import com.josancamon19.rappimoviedatabaseapi.data.models.VideosResponse
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "api_key=5b457fd223b36746a494b0f20be527db"
private const val LANGUAGE = "language=en-US"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface MoviesService {
    @GET("movie/{category}?$API_KEY&$LANGUAGE&page=1")
    fun getMoviesAsync(@Path("category") category: String):
            Deferred<MoviesResponse>

    @GET("genre/movie/list?$API_KEY&$LANGUAGE")
    fun getGenresAsync():
            Deferred<GenresResponse>

    @GET("movie/{movie_id}/videos?$API_KEY&$LANGUAGE")
    fun getMovieVideos(@Path("movie_id") movieId: Long) : Deferred<VideosResponse>
}

object MoviesApi {
    val retrofitService: MoviesService by lazy { retrofit.create(MoviesService::class.java) }
}