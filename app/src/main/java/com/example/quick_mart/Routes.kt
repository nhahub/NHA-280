package com.example.quick_mart

import com.example.quick_mart.dto.Category
import com.example.quick_mart.dto.Product
import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    object Home:Routes

    @Serializable
    data class Details( val productId: Int):Routes

    @Serializable
    data class Categories(val categoryId: Int):Routes

    @Serializable
    object AllProducts : Routes

}