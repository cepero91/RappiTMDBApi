package com.josancamon19.rappimoviedatabaseapi.data.models

import com.squareup.moshi.Json

data class Video(val id: String, val key: String, val name: String, val site: String, val type: String)

data class VideosResponse(val id: Long, @field:Json(name = "results") val videos: List<Video>)