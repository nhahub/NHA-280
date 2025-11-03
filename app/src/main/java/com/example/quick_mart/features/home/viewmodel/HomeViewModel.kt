package com.example.quick_mart.features.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_mart.dto.Category
import com.example.quick_mart.features.categories.repo.CategoriesRepository
import com.example.quick_mart.features.categories.repo.CategoriesRepositoryImp
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val repository: CategoriesRepository
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
}
