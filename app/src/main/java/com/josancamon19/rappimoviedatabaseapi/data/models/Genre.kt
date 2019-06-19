package com.josancamon19.rappimoviedatabaseapi.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class Genre(@PrimaryKey val id: Int, val name: String)

data class GenresResponse(val genres: List<Genre>)
