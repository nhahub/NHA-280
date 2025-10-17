package com.example.quick_mart.features.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_mart.CategoryDto.Category2
import com.example.quick_mart.repository.CategoriesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CategoriesState(
    val isLoading: Boolean = false,
    val categories: List<Category2> = emptyList(),
    val error: String? = null
)


class CategoriesViewModel : ViewModel() {

    private val repository = CategoriesRepository()

    private val _state = MutableStateFlow(CategoriesState())
    val state: StateFlow<CategoriesState> = _state

    fun getAllCategories() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                val response = repository.getAllCategories()
                if (response.isSuccessful) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        categories = response.body() ?: emptyList(),
                        error = null
                    )
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Error: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unexpected error"
                )
            }
        }
    }
}
