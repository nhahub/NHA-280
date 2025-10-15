package com.example.quick_mart.features.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_mart.dto.Product
import com.example.quick_mart.features.home.repo.HomeRepository
import com.example.quick_mart.network.API
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: HomeRepository
) : ViewModel() {


//    private val _products= MutableLiveData<List<Product>>()
//    val listOfProducts : LiveData<List<Product>> =_products

    fun getAllProducts(){
        viewModelScope.launch{
                val response = repo.getProductsResponseFromNetwork()
                if (response.isSuccessful) {
                    val productsList: List<Product> = response.body() ?: emptyList()
                    Log.d("products", productsList.toString())
//                    _products.postValue(productsList)
                } else {
                    Log.e("API Error", "Error: ${response.code()}, ${response.errorBody()?.string()}")
                }

        }
    }
}