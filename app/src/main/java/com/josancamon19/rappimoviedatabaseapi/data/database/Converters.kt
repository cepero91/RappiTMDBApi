package com.josancamon19.rappimoviedatabaseapi.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {


    inline fun <reified T> genericType() = object : TypeToken<List<Int>>() {}.type

    @TypeConverter
    fun fromString(genreIdsJson: String): List<Int> {
        return Gson().fromJson(genreIdsJson, genericType<List<Int>>())

    }

    @TypeConverter
    fun fromIntList(genreIds: List<Int>): String {
        return Gson().toJson(genreIds)
    }
}