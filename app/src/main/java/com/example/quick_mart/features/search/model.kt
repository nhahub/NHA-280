package com.example.quick_mart.features.search.model

import com.example.quick_mart.dto.Product

sealed class SearchUiState {
    object Initial : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val products: List<Product>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
    object Empty : SearchUiState()
}

data class SearchScreenState(
    val searchQuery: String = "",
    val uiState: SearchUiState = SearchUiState.Initial,
    val recentSearches: List<String> = emptyList(),
    val showFilterDialog: Boolean = false,
    val selectedFilter: FilterType = FilterType.NONE,
    val filteredProducts: List<Product> = emptyList()
)

enum class FilterType {
    NONE,
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW,
    NAME_A_TO_Z,
    NAME_Z_TO_A,
    NEWEST,
    OLDEST
}