package com.example.quick_mart.network

import com.example.quick_mart.dto.Product
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("products")
    suspend fun getProductsResponse(): Response<List<Product>>

}