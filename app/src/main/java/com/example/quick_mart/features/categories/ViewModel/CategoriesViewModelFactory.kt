package com.example.quick_mart.features.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quick_mart.features.categories.repo.CategoriesRepository

@Suppress("UNCHECKED_CAST")
class CategoriesViewModelFactory(
    private val repository: CategoriesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoriesViewModel::class.java)) {
            return CategoriesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    private fun CategoriesViewModel(repository: CategoriesRepository) {
        TODO("Not yet implemented")
    }
}
