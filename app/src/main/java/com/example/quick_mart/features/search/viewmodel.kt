package com.example.quick_mart.features.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_mart.dto.Product
import com.example.quick_mart.features.search.model.FilterType
import com.example.quick_mart.features.search.model.SearchScreenState
import com.example.quick_mart.features.search.model.SearchUiState
import com.example.quick_mart.features.search.repository.SearchRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val repository: SearchRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow(SearchScreenState())
    val screenState: StateFlow<SearchScreenState> = _screenState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    private var allProducts: List<Product> = emptyList()

    init {
        loadAllProducts()
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500) // Wait 500ms after user stops typing
                .distinctUntilChanged()
                .collect { query ->
                    _screenState.update { it.copy(searchQuery = query) }
                    if (query.isBlank()) {
                        // Show all products when query is empty
                        _screenState.update {
                            it.copy(
                                uiState = if (allProducts.isEmpty()) {
                                    SearchUiState.Empty
                                } else {
                                    SearchUiState.Success(allProducts)
                                },
                                filteredProducts = allProducts
                            )
                        }
                    } else if (query.length >= 2) {
                        // Start searching when query has at least 2 characters
                        searchProducts(query)
                    }
                }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    private fun loadAllProducts() {
        viewModelScope.launch {
            _screenState.update { it.copy(uiState = SearchUiState.Loading) }

            repository.getAllProducts().collect { result ->
                result.fold(
                    onSuccess = { products ->
                        allProducts = products
                        _screenState.update {
                            it.copy(
                                uiState = if (products.isEmpty()) {
                                    SearchUiState.Empty
                                } else {
                                    SearchUiState.Success(products)
                                },
                                filteredProducts = products
                            )
                        }
                    },
                    onFailure = { error ->
                        _screenState.update {
                            it.copy(
                                uiState = SearchUiState.Error(
                                    error.message ?: "Unknown error occurred"
                                )
                            )
                        }
                    }
                )
            }
        }
    }

    private fun searchProducts(query: String) {
        viewModelScope.launch {
            _screenState.update { it.copy(uiState = SearchUiState.Loading) }

            repository.searchProducts(query).collect { result ->
                result.fold(
                    onSuccess = { products ->
                        addToRecentSearches(query)
                        _screenState.update {
                            it.copy(
                                uiState = if (products.isEmpty()) {
                                    SearchUiState.Empty
                                } else {
                                    SearchUiState.Success(products)
                                },
                                filteredProducts = products
                            )
                        }
                        // Apply current filter if any
                        if (_screenState.value.selectedFilter != FilterType.NONE) {
                            applyFilter(_screenState.value.selectedFilter)
                        }
                    },
                    onFailure = { error ->
                        _screenState.update {
                            it.copy(
                                uiState = SearchUiState.Error(
                                    error.message ?: "Search failed"
                                )
                            )
                        }
                    }
                )
            }
        }
    }

    fun onRecentSearchClick(query: String) {
        _searchQuery.value = query
    }

    fun toggleFilterDialog() {
        _screenState.update {
            it.copy(showFilterDialog = !it.showFilterDialog)
        }
    }

    fun applyFilter(filterType: FilterType) {
        val currentProducts = _screenState.value.filteredProducts

        val sortedProducts = when (filterType) {
            FilterType.PRICE_LOW_TO_HIGH -> {
                currentProducts.sortedBy { it.price ?: Long.MAX_VALUE }
            }
            FilterType.PRICE_HIGH_TO_LOW -> {
                currentProducts.sortedByDescending { it.price ?: 0L }
            }
            FilterType.NAME_A_TO_Z -> {
                currentProducts.sortedBy { it.title ?: it.name ?: "" }
            }
            FilterType.NAME_Z_TO_A -> {
                currentProducts.sortedByDescending { it.title ?: it.name ?: "" }
            }
            FilterType.NEWEST -> {
                currentProducts.sortedByDescending { it.creationAt ?: "" }
            }
            FilterType.OLDEST -> {
                currentProducts.sortedBy { it.creationAt ?: "" }
            }
            FilterType.NONE -> currentProducts
        }

        _screenState.update {
            it.copy(
                selectedFilter = filterType,
                filteredProducts = sortedProducts,
                uiState = if (sortedProducts.isEmpty()) {
                    SearchUiState.Empty
                } else {
                    SearchUiState.Success(sortedProducts)
                },
                showFilterDialog = false
            )
        }
    }

    fun clearFilter() {
        applyFilter(FilterType.NONE)
    }

    private fun addToRecentSearches(query: String) {
        val currentSearches = _screenState.value.recentSearches.toMutableList()

        // Remove if already exists
        currentSearches.remove(query)

        // Add to the beginning
        currentSearches.add(0, query)

        // Keep only last 10 searches
        if (currentSearches.size > 10) {
            currentSearches.removeAt(currentSearches.size - 1)
        }

        _screenState.update {
            it.copy(recentSearches = currentSearches)
        }
    }

    fun clearRecentSearches() {
        _screenState.update {
            it.copy(recentSearches = emptyList())
        }
    }

    fun removeFromRecentSearches(query: String) {
        val currentSearches = _screenState.value.recentSearches.toMutableList()
        currentSearches.remove(query)
        _screenState.update {
            it.copy(recentSearches = currentSearches)
        }
    }

    fun retry() {
        val currentQuery = _screenState.value.searchQuery
        if (currentQuery.isBlank()) {
            loadAllProducts()
        } else {
            searchProducts(currentQuery)
        }
    }
}