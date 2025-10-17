package com.example.quick_mart.CategoryDto

data class CategoryById(
    val creationAt: String,
    val id: Int,
    val image: String,
    val name: String,
    val slug: String,
    val updatedAt: String
)