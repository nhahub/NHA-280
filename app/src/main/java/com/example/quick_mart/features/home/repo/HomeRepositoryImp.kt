package com.example.quick_mart.features.home.repo

import com.example.quick_mart.dto.Product
import com.example.quick_mart.network.RemoteDataSource
import retrofit2.Response

class HomeRepositoryImp(
    private val remoteDataSource: RemoteDataSource
) : HomeRepository {

    override suspend fun getProductsResponseFromNetwork(): Response<List<Product>> {
        return remoteDataSource.getProductsResponseFromNetwork()
    }
}