package com.example.quick_mart.db

import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product

interface LocalDataSource {
    suspend fun insertProduct(product: Product)
    suspend fun getAllProducts(): List<Product>
    suspend fun clearAllProducts()
    suspend fun insertCategory(category: Category)
    suspend fun getAllCategories(): List<Category>
    suspend fun clearAllCategories()

    //favorites
    suspend fun updateFavoriteStatus(productId: Int, isFav: Boolean)
    suspend fun getFavoriteProducts(): List<Product>



}