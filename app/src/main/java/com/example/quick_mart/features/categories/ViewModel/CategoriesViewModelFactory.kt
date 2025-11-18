package com.example.quick_mart.features.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quick_mart.features.categories.repo.CategoriesRepository
import com.example.quick_mart.features.home.repo.HomeRepository

@Suppress("UNCHECKED_CAST")
class CategoriesViewModelFactory(
    private val repository: CategoriesRepository,
    private val homeRepository: HomeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoriesViewModel::class.java)) {
            return CategoriesViewModel(repository, homeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
