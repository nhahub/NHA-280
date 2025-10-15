package com.example.quick_mart.network

import com.example.quick_mart.dto.Product
import com.example.quick_mart.network.API.apiService
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getProductsResponseFromNetwork(): Response<List<Product>>
}