package com.example.quick_mart

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    object Home:Routes

    @Serializable
    data class Details( val productId: Int):Routes

    @Serializable
    data class Category(val categoryId: Int):Routes

    @Serializable
    object Categories:Routes

    @Serializable
    object AllProducts : Routes

}