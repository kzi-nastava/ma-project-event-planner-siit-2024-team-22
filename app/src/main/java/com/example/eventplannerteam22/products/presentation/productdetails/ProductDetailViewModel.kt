package com.example.eventplannerteam22.products.presentation.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.products.data.repository.ProductRepositoryImpl
import com.example.eventplannerteam22.products.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepositoryImpl
) : ViewModel() {
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _product.value = repository.getProduct(id)
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error"
            }
            _isLoading.value = false
        }
    }
}