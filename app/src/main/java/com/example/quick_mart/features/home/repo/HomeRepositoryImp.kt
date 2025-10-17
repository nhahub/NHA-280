package com.example.quick_mart.features.home.repo

import com.example.quick_mart.db.LocalDataSource
import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import com.example.quick_mart.network.RemoteDataSource
import retrofit2.Response

class HomeRepositoryImp(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource

) : HomeRepository {

    override suspend fun getProductsResponseFromNetwork(): Response<List<Product>> {
        return remoteDataSource.getProductsResponseFromNetwork()
    }

    override suspend fun getCategoriesResponseFromNetwork(): Response<List<Category>> {
        return remoteDataSource.getCategoriesResponseFromNetwork()
    }

    override suspend fun insertLocalProduct(product: Product) {
        localDataSource.insertProduct(product)
    }

    override suspend fun getAllLocalProducts(): List<Product> {
        return localDataSource.getAllProducts()
    }

    override suspend fun clearAllLocalProducts() {
        localDataSource.clearAllProducts()
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
}