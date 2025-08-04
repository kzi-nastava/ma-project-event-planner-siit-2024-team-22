package com.example.eventplannerteam22.products.presentation.editproduct

sealed class UpdateProductUiEvent {
    data class NameChanged(val value: String) : UpdateProductUiEvent()
    data class DescriptionChanged(val value: String) : UpdateProductUiEvent()
    data class PriceChanged(val value: String) : UpdateProductUiEvent()
    data class DiscountChanged(val value: String) : UpdateProductUiEvent()
    data class IsPrivateChanged(val value: Boolean) : UpdateProductUiEvent()
    data class Submit(val productId: Int) : UpdateProductUiEvent()
}
