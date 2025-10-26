package com.example.quick_mart.network

import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import com.example.quick_mart.network.API.apiService
import retrofit2.Response

class RemoteDataSourceImp: RemoteDataSource {
    override suspend fun getProductsResponseFromNetwork(): Response<List<Product>> {
        return apiService.getProductsResponse()
    }

    override suspend fun getCategoriesResponseFromNetwork(): Response<List<Category>> {
        return apiService.getCategoriesResponse()
    }
}