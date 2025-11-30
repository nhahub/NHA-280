package com.example.quick_mart.features.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quick_mart.db.LocalDataSource

class CartViewModelFactory(
    private val localDataSource: LocalDataSource
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(localDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}