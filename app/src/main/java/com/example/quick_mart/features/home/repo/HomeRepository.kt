package com.example.quick_mart.features.home.repo

import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import retrofit2.Response

interface HomeRepository {
    suspend fun getProductsResponseFromNetwork(): Response<List<Product>>
    suspend fun getCategoriesResponseFromNetwork(): Response<List<Category>>
    suspend fun insertLocalProduct(product: Product)
    suspend fun getAllLocalProducts(): List<Product>
    suspend fun clearAllLocalProducts()
    suspend fun insertLocalCategory(category: Category)
    suspend fun getAllLocalCategories(): List<Category>
    suspend fun clearAllLocalCategories()

    //favorite
    suspend fun updateFavoriteStatus(productId: Int, isFav: Boolean)
    suspend fun getFavoriteProducts(): List<Product>


}