package com.example.eventplannerteam22.productcategory.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.productcategory.data.ProductCategoryRepository
import com.example.eventplannerteam22.productcategory.domen.ProductCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductCategoryViewModel @Inject constructor(
    private val repository: ProductCategoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductCategoryState())
    val state: StateFlow<ProductCategoryState> = _state.asStateFlow()

    private var _selectedCategory = MutableStateFlow<ProductCategory?>(null)
    val selectedCategory: StateFlow<ProductCategory?> = _selectedCategory.asStateFlow()

    fun setSelectedCategory(category: ProductCategory?) {
        _selectedCategory.value = category
    }

    init {
        loadProductCategories()
    }

    private fun loadProductCategories(limit: Int = 10, offset: Int = 0) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val categories = repository.getProductCategories(limit, offset)
                _state.update {
                    it.copy(
                        categories = categories,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load categories"
                    )
                }
            }
        }
    }

    fun addProductCategory(category: ProductCategory) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                repository.addProductCategory(category)
                loadProductCategories() // Refresh the list
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to add category"
                    )
                }
            }
        }
    }

    fun updateProductCategory(category: ProductCategory, id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                repository.updateProductCategory(category, id)
                loadProductCategories() // Refresh the list
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to update category"
                    )
                }
            }
        }
    }

    fun deleteProductCategory(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                repository.deleteProductCategory(id)
                loadProductCategories() // Refresh the list
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to delete category"
                    )
                }
            }
        }
    }

    fun retry() {
        loadProductCategories()
    }
}

data class ProductCategoryState(
    val categories: List<ProductCategory> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)