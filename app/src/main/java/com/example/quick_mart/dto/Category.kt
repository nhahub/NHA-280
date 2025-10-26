package com.example.quick_mart.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "categories")
data class Category(
    val creationAt: String?,
    @PrimaryKey val id: Int?,
    val image: String?,
    val name: String?,
    val slug: String?,
    val updatedAt: String?
)
