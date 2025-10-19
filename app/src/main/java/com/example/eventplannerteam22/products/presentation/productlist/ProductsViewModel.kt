package com.example.eventplannerteam22.products.presentation.productlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.products.data.repository.ProductRepositoryImpl
import com.example.eventplannerteam22.products.data.toProductListItem
import com.example.eventplannerteam22.products.domain.ProductListItem
import com.example.eventplannerteam22.products.presentation.filters.ProductFilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductRepositoryImpl,
    private val categoryRepository: com.example.eventplannerteam22.productcategory.data.ProductCategoryRepository
) : ViewModel() {

    var products by mutableStateOf<List<ProductListItem>>(emptyList())
        private set

    var filteredProducts by mutableStateOf<List<ProductListItem>>(emptyList())
        private set

    var isFiltered by mutableStateOf(false)
        private set

    var filterState by mutableStateOf(ProductFilterState())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var hasMoreProducts by mutableStateOf(true)
        private set

    private var currentOffset = 0
    private val limit = 3

    init {
        loadProducts()
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = categoryRepository.getProductCategories(100, 0) // Загрузим все категории
                updateFilterState(filterState.copy(categories = categories))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadProducts() {
        if (isLoading || !hasMoreProducts) return

        isLoading = true
        viewModelScope.launch {
            try {
                val newProducts = repository.getProducts(limit, currentOffset)
                println("New products: $newProducts")
                products = products + newProducts
                filteredProducts = products
                currentOffset += limit

                hasMoreProducts = newProducts.size == limit
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun updateFilterState(newFilterState: ProductFilterState) {
        filterState = newFilterState
    }

    fun applyFilters() {
        viewModelScope.launch {
            isLoading = true
            
            try {
                val minPrice = filterState.minPrice.toDoubleOrNull()
                val maxPrice = filterState.maxPrice.toDoubleOrNull()
                val minDiscount = filterState.minDiscount.toDoubleOrNull()
                val maxDiscount = filterState.maxDiscount.toDoubleOrNull()
                
                println("Filter params - minPrice: $minPrice, maxPrice: $maxPrice, minDiscount: $minDiscount, maxDiscount: $maxDiscount")
                
                val result = repository.searchAndFilterProducts(
                    name = filterState.name.takeIf { it.isNotEmpty() },
                    description = filterState.description.takeIf { it.isNotEmpty() },
                    categoryId = filterState.selectedCategory?.id,
                    minPrice = minPrice,
                    maxPrice = maxPrice,
                    minDiscount = minDiscount,
                    maxDiscount = maxDiscount,
                    imageSource = null,
                    isPrivate = when (filterState.isPrivate) {
                        "Yes" -> true
                        "No" -> false
                        else -> null
                    }
                )
                
                when (result) {
                    is com.example.eventplannerteam22.network.ApiResult.Success -> {
                        filteredProducts = result.data.map { dto ->
                            dto.toProductListItem()
                        }
                        isFiltered = true
                    }
                    else -> {
                        // В случае ошибки показываем пустой список
                        println("Error during filtering")
                        filteredProducts = emptyList()
                        isFiltered = true
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                filteredProducts = products
                isFiltered = false
            } finally {
                isLoading = false
            }
        }
    }

    fun clearFilters() {
        filterState = ProductFilterState()
        filteredProducts = products
        isFiltered = false
    }
}