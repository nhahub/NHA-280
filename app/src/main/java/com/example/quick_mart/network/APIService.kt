package com.example.quick_mart.network

import com.example.quick_mart.dto.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("products")
    suspend fun getProductsResponse(): Response<List<Product>>
    suspend fun getProductById(@Path("id") id: Int): Response<Product>
    //  @Path("id") id: Int
}