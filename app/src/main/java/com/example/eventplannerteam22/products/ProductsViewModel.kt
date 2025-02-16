package com.example.eventplannerteam22.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var hasMoreProducts by mutableStateOf(true)
        private set

    private var currentOffset = 0
    private val limit = 3

    init {
        loadProducts()
    }

    fun loadProducts() {
        if (isLoading || !hasMoreProducts) return

        isLoading = true
        viewModelScope.launch {
            try {
                val newProducts = repository.getProducts(limit, currentOffset)
                println("New products: $newProducts")
                products = products + newProducts
                currentOffset += limit

                hasMoreProducts = newProducts.size == limit
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }


}