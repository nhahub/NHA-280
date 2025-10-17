package com.example.quick_mart.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quick_mart.features.home.repo.HomeRepository

class HomeViewModelFactory (
    private val repo: HomeRepository
): ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(repo) as T
        } else {
            throw IllegalArgumentException("HomeViewModel Not Found")
        }
    }
}