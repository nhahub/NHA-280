package com.example.quick_mart.features.home.repo

import com.example.quick_mart.dto.Product
import retrofit2.Response

interface HomeRepository {
    suspend fun getProductsResponseFromNetwork(): Response<List<Product>>
}