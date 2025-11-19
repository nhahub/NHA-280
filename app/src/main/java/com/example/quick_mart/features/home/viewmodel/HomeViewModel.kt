package com.example.quick_mart.features.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import com.example.quick_mart.features.home.repo.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repo: HomeRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getResponseAndCache() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                // Perform network calls on IO dispatcher
                withContext(Dispatchers.IO) {
                    val productsResponse = repo.getProductsResponseFromNetwork()
                    val categoriesResponse = repo.getCategoriesResponseFromNetwork()

                    // Check products response
                    if (productsResponse.isSuccessful) {
                        val productsList: List<Product> = productsResponse.body() ?: emptyList()

                        // Update UI state
                        _products.emit(productsList)

                        // Cache products locally
                        repo.clearAllLocalProducts()
                        for (product in productsList) {
                            repo.insertLocalProduct(product)
                        }

                        Log.d("HomeViewModel", "Products fetched: ${productsList.size}")
                        val cachedProducts = repo.getAllLocalProducts()
                        Log.d("HomeViewModel", "Cached products: ${cachedProducts.size}")
                    } else {
                        val errorMsg = "Products API Error: ${productsResponse.code()}"
                        Log.e("HomeViewModel", errorMsg)
                        _error.emit(errorMsg)
                    }

                    // Check categories response
                    if (categoriesResponse.isSuccessful) {
                        val categoriesList = categoriesResponse.body() ?: emptyList()

                        // Update UI state
                        _categories.emit(categoriesList)

                        // Cache categories locally
                        repo.clearAllLocalCategories()
                        for (category in categoriesList) {
                            repo.insertLocalCategory(category)
                        }

                        Log.d("HomeViewModel", "Categories fetched: ${categoriesList.size}")
                        val cachedCategories = repo.getAllLocalCategories()
                        Log.d("HomeViewModel", "Cached categories: ${cachedCategories.size}")
                    } else {
                        val errorMsg = "Categories API Error: ${categoriesResponse.code()}"
                        Log.e("HomeViewModel", errorMsg)
                        _error.emit(errorMsg)
                    }
                }
            } catch (e: Exception) {
                val errorMsg = "Error fetching data: ${e.message}"
                Log.e("HomeViewModel", errorMsg, e)
                _error.emit(errorMsg)

                // Try to load cached data as fallback
                loadCachedData()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun loadCachedData() {
        try {
            withContext(Dispatchers.IO) {
                val cachedProducts = repo.getAllLocalProducts()
                val cachedCategories = repo.getAllLocalCategories()

                _products.emit(cachedProducts)
                _categories.emit(cachedCategories)

                Log.d("HomeViewModel", "Loaded cached data - Products: ${cachedProducts.size}, Categories: ${cachedCategories.size}")
            }
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Error loading cached data: ${e.message}", e)
        }
    }
}