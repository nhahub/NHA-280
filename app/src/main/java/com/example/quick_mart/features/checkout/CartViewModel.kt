package com.example.quick_mart.features.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_mart.db.LocalDataSource
import com.example.quick_mart.dto.CartItemEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class CartViewModel(
    private val localDataSource: LocalDataSource
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItemEntity>>(emptyList())
    val cartItems: StateFlow<List<CartItemEntity>> = _cartItems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            localDataSource.getCartItems().collect { items ->
                _cartItems.value = items
            }
        }
    }

    fun addToCart(productId: Int, title: String, price: Double, imageUrl: String) {
        viewModelScope.launch {
            val existingItem = localDataSource.getCartItemByProductId(productId)
            if (existingItem != null) {
                // Increment quantity
                localDataSource.updateCartQuantity(productId, existingItem.quantity + 1)
            } else {
                // Add new item
                val cartItem = CartItemEntity(
                    productId = productId,
                    title = title,
                    price = price,
                    imageUrl = imageUrl,
                    quantity = 1
                )
                localDataSource.insertCartItem(cartItem)
            }
        }
    }

    fun updateQuantity(productId: Int, newQuantity: Int) {
        viewModelScope.launch {
            if (newQuantity <= 0) {
                localDataSource.deleteCartItem(productId)
            } else {
                localDataSource.updateCartQuantity(productId, newQuantity)
            }
        }
    }

    fun removeItem(productId: Int) {
        viewModelScope.launch {
            localDataSource.deleteCartItem(productId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            localDataSource.clearCart()
        }
    }

    fun getCartTotal(): Double {
        return _cartItems.value.sumOf { it.price * it.quantity }
    }

    fun getCartItemCount(): Int {
        return _cartItems.value.sumOf { it.quantity }
    }
}