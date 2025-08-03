package com.example.eventplannerteam22.products.presentation.createproduct

sealed class CreateProductUiEvent {
    data class NameChanged(val value: String) : CreateProductUiEvent()
    data class DescriptionChanged(val value: String) : CreateProductUiEvent()
    data class PriceChanged(val value: String) : CreateProductUiEvent()
    data class DiscountChanged(val value: String) : CreateProductUiEvent()
    data class IsPrivateChanged(val value: Boolean) : CreateProductUiEvent()
    data class Submit(val userId: Int) : CreateProductUiEvent()
}