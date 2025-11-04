package com.example.quick_mart.features.categories.repo

import com.example.quick_mart.db.LocalDataSource
import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import com.example.quick_mart.network.RemoteDataSource
import retrofit2.Response

class CategoriesRepositoryImp(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CategoriesRepository {

    // CATEGORIES

    override suspend fun getCategoriesFromNetwork(): Response<List<Category>> {
        return remoteDataSource.getCategoriesResponseFromNetwork()
    }

    override suspend fun insertLocalCategory(category: Category) {
        localDataSource.insertCategory(category)
    }

    override suspend fun getAllLocalCategories(): List<Category> {
        return localDataSource.getAllCategories()
    }

    override suspend fun clearAllLocalCategories() {
        localDataSource.clearAllCategories()
    }

    //  PRODUCTS

    // Get products for a specific category (Network + Local fallback)
    override suspend fun getProductsByCategory(categoryId: Int): List<Product> {
        return try {
            val response = remoteDataSource.getProductsByCategory(categoryId)
            if (response.isSuccessful) {
                val apiProducts = response.body() ?: emptyList()

                clearProductsByCategory(categoryId)

                apiProducts.forEach { localDataSource.insertProduct(it) }

                apiProducts
            } else {
                // if the API call fails, try to get products from the local database
                localDataSource.getAllProducts()
                    .filter { it.category?.id == categoryId }
            }
        } catch (e: Exception) {
            // if there's an exception, try to get products from the local database
            localDataSource.getAllProducts()
                .filter { it.category?.id == categoryId }
        }
    }

    override suspend fun insertLocalProduct(product: Product) {
        localDataSource.insertProduct(product)
    }

    // if the API call fails, try to get products from the local database
    override suspend fun clearProductsByCategory(categoryId: Int) {
        val allProducts = localDataSource.getAllProducts()
        val filteredProducts = allProducts.filter { it.category?.id == categoryId }
        filteredProducts.forEach { localDataSource.clearAllProducts() }
    }
}
