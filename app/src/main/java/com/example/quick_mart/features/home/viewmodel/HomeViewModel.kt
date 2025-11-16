package com.example.quick_mart.features.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_mart.dto.Category
import com.example.quick_mart.features.categories.repo.CategoriesRepository
import com.example.quick_mart.features.categories.repo.CategoriesRepositoryImp
import kotlinx.coroutines.launch
import com.example.quick_mart.dto.Product
import com.example.quick_mart.features.home.repo.HomeRepository

class CategoriesViewModel(
    private val repository: CategoriesRepository,
    private val homeRepository: HomeRepository,
    private val repo: HomeRepository
) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllCategories() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val data = repository.getAllCategories()
            _categories.postValue(data)
            _isLoading.postValue(false)
        }
    }

    fun refreshCategories() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val refreshed = repository.refreshCategories()
            _categories.postValue(refreshed)
            _isLoading.postValue(false)
        }
    }
    //favorites
    fun toggleFavorite(productId: Int, isFav: Boolean) {
        viewModelScope.launch {
            repo.updateFavoriteStatus(productId, isFav)
        }
    }

    fun getFavoriteProducts(onResult: (List<Product>) -> Unit) {
        viewModelScope.launch {
            val favorites = repo.getFavoriteProducts()
            onResult(favorites)
        }
    }
    // list of favorite products
    private val _favoriteProducts = MutableLiveData<List<Product>>()
    val favoriteProducts: LiveData<List<Product>> = _favoriteProducts

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            val newStatus = !product.isFavorite
            product.isFavorite = newStatus
            repo.updateFavoriteStatus(product.id, newStatus)
            refreshFavorites()
        }
    }

    fun refreshFavorites() {
        viewModelScope.launch {
            _favoriteProducts.postValue(repo.getFavoriteProducts())
        }
    }




}
