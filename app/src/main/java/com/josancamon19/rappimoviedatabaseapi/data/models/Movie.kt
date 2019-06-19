package com.josancamon19.rappimoviedatabaseapi.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "database_id") val databaseId: Int,
    val id: Long,
    @ColumnInfo(name = "original_title") @field:Json(name = "original_title") val title: String,
    val overview: String,
    @ColumnInfo(name = "original_language") @field:Json(name = "original_language") val originalLanguage: String,
    val popularity: Double,
    @ColumnInfo(name = "vote_average") @field:Json(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") @field:Json(name = "vote_count") val voteCount: Int,
    @ColumnInfo(name = "poster_path") @field:Json(name = "poster_path") val posterPath: String,
    val video: Boolean,
    @ColumnInfo(name = "genre_ids") @field:Json(name = "genre_ids") val genreIds: List<Int>,
    val adult: Boolean,
    @ColumnInfo(name = "release_date") @field:Json(name = "release_date") val releaseDate: String,
    var category: String
) : Parcelable


data class MoviesResponse(
    val page: Int,
    @field:Json(name = "total_results") val totalResults: Int,
    @field:Json(name = "total_pages") val totalPages: Int,
    @field:Json(name = "results") val movies: List<Movie>
)
