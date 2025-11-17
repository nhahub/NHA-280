package com.example.quick_mart.features.categories.repo

import com.example.quick_mart.db.LocalDataSource
import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import com.example.quick_mart.network.RemoteDataSource
import retrofit2.Response
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import android.util.Log
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

    override suspend fun getProductsFromNetwork(): Response<List<Product>> {
        return remoteDataSource.getProductsResponseFromNetwork()
    }

    override suspend fun insertLocalProduct(product: Product) {
        localDataSource.insertProduct(product)
    }

    override suspend fun getProductsByCategoryName(categoryName: String): List<Product> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getProductsResponseFromNetwork()
                val allProducts = if (response.isSuccessful && response.body() != null) {
                    response.body()!!
                } else {
                    emptyList()
                }

                val filtered = allProducts.filter { it.category?.name.equals(categoryName, ignoreCase = true) }
                filtered
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    override suspend fun clearAllProducts() {
        localDataSource.clearAllProducts()
        }
    }
