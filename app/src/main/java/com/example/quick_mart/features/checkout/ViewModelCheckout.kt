package com.example.quick_mart.features.checkout

import android.util.Log
import androidx.collection.emptyIntList
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_mart.dto.Product
import com.example.quick_mart.network.API.apiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelCheckout: ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Product state
    private val _productState = MutableStateFlow<Product?>(null)
    val productState: StateFlow<Product?> = _productState
    var _productsList = MutableLiveData<Product>()

    fun getProductId(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiService.getProductsId(id)
            if (response.isSuccessful){
                val productId = response.body()
                _productsList.postValue(productId)
            } else {
                Log.e("asd -->", "getProductId: ${response.errorBody()}")
            }
        }
    }
}