package com.example.quick_mart.Detailshome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_mart.dto.Product
import com.example.quick_mart.network.API.apiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelDetails : ViewModel() {

    private val _product = MutableLiveData<Product>()
    val productdetails: LiveData<Product> get() = _product

    fun getProductById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getProductById(id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _product.postValue(it)
                    }
                } else {
                    Log.e("API", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API", "Exception: ${e.localizedMessage}", e)
            }
        }
    }
}
