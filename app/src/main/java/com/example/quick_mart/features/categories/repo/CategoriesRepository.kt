package com.example.quick_mart.features.categories.repo

import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import retrofit2.Response

interface CategoriesRepository {

    //  Categories
    suspend fun getCategoriesFromNetwork(): Response<List<Category>>
    suspend fun insertLocalCategory(category: Category)
    suspend fun getAllLocalCategories(): List<Category>
    suspend fun clearAllLocalCategories()

    // Products
    suspend fun insertLocalProduct(product: Product)
    suspend fun getProductsByCategoryName(categoryName: String): List<Product>
    suspend fun getProductsFromNetwork(): Response<List<Product>>

    suspend fun clearAllProducts()
}
