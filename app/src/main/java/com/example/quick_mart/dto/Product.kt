package com.example.quick_mart.dto

data class Product(
    val category: Category?,
    val creationAt: String?,
    val description: String?,
    val id: Int?,
    val name: String,
    val images: List<String?>?,
    val price: Int?,
    val slug: String?,
    val title: String?,
    val updatedAt: String?
)
