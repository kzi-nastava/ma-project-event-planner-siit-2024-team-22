package com.example.eventplannerteam22.products.presentation.createproduct

import androidx.lifecycle.ViewModel
import com.example.eventplannerteam22.products.data.repository.ProductRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject


@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val repository: ProductRepositoryImpl
) : ViewModel() {

    private val _addProductResult = MutableSharedFlow<AddProductResult>()
    val addProductResult = _addProductResult.asSharedFlow()

//    suspend fun addProduct(name: String, description: String, category: String, price: String, discount: String) {
//        try {
//            repository.createProduct(
//                Product(0, name, description, category, price.toBigDecimal(), discount.toBigDecimalOrNull(), null, emptyList(), false)
//            )
//            _addProductResult.emit(AddProductResult.Success)
//        } catch (e: Exception) {
//            _addProductResult.emit(AddProductResult.Failure(e.message ?: "Unknown error"))
//        }
//    }
}

sealed class AddProductResult {
    object Success : AddProductResult()
    data class Failure(val message: String) : AddProductResult()
}
