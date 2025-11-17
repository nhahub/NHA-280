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

    override suspend fun getProductsFromNetwork(): Response<List<Product>> {
        return remoteDataSource.getProductsResponseFromNetwork()
    }

    override suspend fun insertLocalProduct(product: Product) {
        localDataSource.insertProduct(product)
    }

    override suspend fun getProductsByCategoryName(categoryName: String): List<Product> {
        val allProducts = localDataSource.getAllProducts()
        return allProducts.filter { product ->
            val catName = product.category?.name
            catName != null && catName.equals(categoryName, ignoreCase = true)
        }
    }

    override suspend fun clearAllProducts() {
        localDataSource.clearAllProducts()
        }
    }
