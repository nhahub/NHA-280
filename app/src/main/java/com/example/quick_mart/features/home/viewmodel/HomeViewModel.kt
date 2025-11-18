package com.example.quick_mart.features.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import com.example.quick_mart.features.home.repo.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: HomeRepository
) : ViewModel() {


//    private val _products= MutableLiveData<List<Product>>()
//    val listOfProducts : LiveData<List<Product>> =_products

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    fun getResponseAndCache(){
        viewModelScope.launch{
            val productsResponse = repo.getProductsResponseFromNetwork()
            val categoriesResponse = repo.getCategoriesResponseFromNetwork()
            if (productsResponse.isSuccessful) {
                val productsList: List<Product> = productsResponse.body() ?: emptyList()
                // Cache products locally
                repo.clearAllLocalProducts()
                for (product in productsList) {
                    repo.insertLocalProduct(product)
                }
                Log.d("products", productsList.toString())
                val cachedProducts = repo.getAllLocalProducts()
                Log.d("cachedProducts", cachedProducts.toString())

                val categoriesList = categoriesResponse.body() ?: emptyList()
                // Cache categories locally
                repo.clearAllLocalCategories()
                for (category in categoriesList) {
                    repo.insertLocalCategory(category)
                }
                Log.d("categories", categoriesList.toString())
                val cachedCategories = repo.getAllLocalCategories()
                Log.d("cachedCategories", cachedCategories.toString())

//                    _products.postValue(productsList)
            } else {
                Log.e("API Error", "Error: ${productsResponse.code()}, ${productsResponse.errorBody()?.string()}")
            }

        }
    }
}