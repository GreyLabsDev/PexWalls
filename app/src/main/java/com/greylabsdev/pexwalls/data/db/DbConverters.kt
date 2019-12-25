package com.greylabsdev.pexwalls.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity

class DbConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringPhoto(source: String): PhotoDbEntity {
        val type = object : TypeToken<PhotoDbEntity>() {}.type
        return gson.fromJson(source, type)
    }

    @TypeConverter
    fun toString(source: PhotoDbEntity): String {
        val type = object : TypeToken<PhotoDbEntity>() {}.type
        return gson.toJson(source, type)
    }
}
