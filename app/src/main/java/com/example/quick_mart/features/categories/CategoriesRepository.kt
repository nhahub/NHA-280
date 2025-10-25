package com.example.quick_mart.repository

import com.example.quick_mart.network.API
import com.example.quick_mart.CategoryDto.*

class CategoriesRepository {

    suspend fun getAllCategories() = API.apiService.getCategoriesResponse()

    suspend fun getCategoryById(id: Int) = API.apiService.getCategoryById(id)

    suspend fun getCategoryProducts(id: Int) = API.apiService.getCategoryProducts(id)
}
