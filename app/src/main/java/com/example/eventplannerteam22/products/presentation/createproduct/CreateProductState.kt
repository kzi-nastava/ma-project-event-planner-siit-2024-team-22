package com.example.eventplannerteam22.products.presentation.createproduct

data class CreateProductState(
    val name: String = "",
    val nameError: String? = null,
    val description: String = "",
    val descriptionError: String? = null,
    val price: String = "",
    val priceError: String? = null,
    val discount: String = "",
    val discountError: String? = null,
    val isPrivate: Boolean = false
)
