package com.example.quick_mart.features.categories.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import com.example.quick_mart.features.categories.repo.CategoriesRepository
import kotlinx.coroutines.launch
import com.example.quick_mart.features.home.repo.HomeRepository
class CategoriesViewModel(
    private val repository: CategoriesRepository,

    private val homeRepository: HomeRepository
) : ViewModel() {

    // CATEGORIES DATA

    private val _categories = MutableLiveData<List<Category>>(emptyList())
    val categories: LiveData<List<Category>> = _categories


    //  PRODUCTS DATA
    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>> = _products


    //  CATEGORY SELECTION STATE

    // save the selected category ID
    // CATEGORY SELECTION STATE
    private val _selectedCategoryName = MutableLiveData<String?>(null)
    val selectedCategoryName: LiveData<String?> = _selectedCategoryName


    //  LOADING STATE

    // بنستخدمها علشان نظهر لودينج سبينر أثناء تحميل الداتا
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading


    init {
        fetchCategories()
    }


    //  FETCH CATEGORIES
    fun fetchCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getCategoriesFromNetwork()
                if (response.isSuccessful) {
                    val list = response.body() ?: emptyList()
                    _categories.value = list
                } else {

                    _categories.value = repository.getAllLocalCategories()
                }
            } catch (e: Exception) {

                _categories.value = repository.getAllLocalCategories()
            } finally {
                _isLoading.value = false
            }
        }
    }


    // FETCH PRODUCTS BY CATEGORY (using category name)
    fun selectCategory(categoryName: String) {
        _selectedCategoryName.value = categoryName // حفظ الاسم
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val productsList = repository.getProductsByCategoryName(categoryName)
                _products.value = productsList
            } catch (e: Exception) {
                _products.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // CLEAR CATEGORY SELECTION
    fun clearSelectedCategory() {
        _selectedCategoryName.value = null
        _products.value = emptyList()
    }
}


    private val _favoriteProducts = MutableLiveData<List<Product>>()
    val favoriteProducts: LiveData<List<Product>> = _favoriteProducts
    // تغيير حالة favorite
    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            val newStatus = !product.isFavorite
            product.isFavorite = newStatus

            homeRepository.updateFavoriteStatus(product.id, newStatus)

            // تحديث LiveData عشان Compose يتحدث فورًا
            _products.value = _products.value?.map {
                if(it.id == product.id) it.copy(isFavorite = newStatus) else it
            }
        }
    }

    // جلب كل المنتجات المفضلة
    fun refreshFavorites() {
        viewModelScope.launch {
            val list = homeRepository.getFavoriteProducts()
            _favoriteProducts.postValue(list)
        }
    }
}
