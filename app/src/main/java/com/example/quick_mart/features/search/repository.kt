package com.example.quick_mart.features.search.repository

import com.example.quick_mart.dto.Product
import com.example.quick_mart.network.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepository(
    private val remoteDataSource: RemoteDataSource
) {
    fun searchProducts(query: String): Flow<Result<List<Product>>> = flow {
        try {
            val response = remoteDataSource.getProductsResponseFromNetwork()
            if (response.isSuccessful) {
                val products = response.body() ?: emptyList()
                // Filter products based on search query
                val filteredProducts = if (query.isBlank()) {
                    products
                } else {
                    products.filter { product ->
                        product.title?.contains(query, ignoreCase = true) == true ||
                                product.name?.contains(query, ignoreCase = true) == true ||
                                product.description?.contains(query, ignoreCase = true) == true ||
                                product.category?.name?.contains(query, ignoreCase = true) == true
                    }
                }
                emit(Result.success(filteredProducts))
            } else {
                emit(Result.failure(Exception("Failed to fetch products: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getAllProducts(): Flow<Result<List<Product>>> = flow {
        try {
            val response = remoteDataSource.getProductsResponseFromNetwork()
            if (response.isSuccessful) {
                val products = response.body() ?: emptyList()
                emit(Result.success(products))
            } else {
                emit(Result.failure(Exception("Failed to fetch products: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}