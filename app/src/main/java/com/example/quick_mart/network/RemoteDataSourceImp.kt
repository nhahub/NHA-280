package com.example.quick_mart.network

import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import com.example.quick_mart.network.API.apiService
import retrofit2.Response

class RemoteDataSourceImp: RemoteDataSource {
//    override suspend fun getProductsResponseFromNetwork(): UiState<List<Product>> {
//        return try {
//            val response = apiService.getProductsResponse()
//            if (response.isSuccessful){
//                val products = response.body()?: emptyList()
//                UiState.Success(products)
//            }else{
//                UiState.Error(response.message())
//            }
//
//        }catch (e: Exception){
//
//            UiState.Error(e.message?: "Unknown error")
//        }
//    }


override suspend fun getProductsResponseFromNetwork(): Response<List<Product>> {
        return apiService.getProductsResponse()
    }

//    override suspend fun getCategoriesResponseFromNetwork(): UiState<List<Category>> {
//        return try {
//            val response = apiService.getCategoriesResponse()
//            if (response.isSuccessful){
//                val categories = response.body()?: emptyList()
//                UiState.Success(categories)
//            }else{
//                UiState.Error(response.message())
//            }
//
//        }catch (e : Exception){
//            UiState.Error(e.message?: "Unknown error")
//        }
//    }
//   // override suspend fun getProductsByCategory(categoryId: Int): Response<List<Product>> {
//   //   return apiService.getProductsByCategoryResponse(categoryId)
//   // }
//}

override suspend fun getCategoriesResponseFromNetwork(): Response<List<Category>> {
        return apiService.getCategoriesResponse()
    }
   // override suspend fun getProductsByCategory(categoryId: Int): Response<List<Product>> {
   //   return apiService.getProductsByCategoryResponse(categoryId)
   // }
}