package com.example.quick_mart.network

import com.example.quick_mart.CategoryDto.Category2
import com.example.quick_mart.CategoryDto.CategoryById
import com.example.quick_mart.CategoryDto.CategoryProductsItem
import com.example.quick_mart.dto.Product
import retrofit2.Response
import retrofit2.http.GET
import com.example.quick_mart.dto.Category
import retrofit2.http.Path


interface APIService {

    @GET("products")
    suspend fun getProductsResponse(): Response<List<Product>>
    // https://api.escuelajs.co/api/v1/categories
    @GET("categories")
    suspend fun getCategoriesResponse(): Response<List<Category2>>
    //https://api.escuelajs.co/api/v1/categories/1/products
    @GET("categories/{id}/products")
    suspend fun getCategoryProducts(@Path("id") id : Int): Response<List<CategoryProductsItem>>
    //https://api.escuelajs.co/api/v1/categories/1
    @GET("categories/{id}")
    suspend fun getCategoryById(@Path("id")id : Int): Response<CategoryById>





}