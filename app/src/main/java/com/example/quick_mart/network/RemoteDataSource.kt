package com.example.quick_mart.network

import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getProductsResponseFromNetwork(): Response<List<Product>>
    suspend fun getCategoriesResponseFromNetwork(): Response<List<Category>>
    //suspend fun getProductsByCategory(categoryId: Int): Response<List<Product>>

}