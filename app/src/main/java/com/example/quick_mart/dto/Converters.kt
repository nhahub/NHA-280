package com.example.quick_mart.dto

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<String?>? {
        if (value.isNullOrEmpty()) return null
        val type = object : TypeToken<List<String?>>() {}.type
        return gson.fromJson<List<String?>>(value, type)
    }

    @TypeConverter
    fun listToString(list: List<String?>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromCategoryString(value: String?): Category? {
        if (value.isNullOrEmpty()) return null
        return gson.fromJson(value, Category::class.java)
    }

    @TypeConverter
    fun categoryToString(category: Category?): String? {
        return gson.toJson(category)
    }
}