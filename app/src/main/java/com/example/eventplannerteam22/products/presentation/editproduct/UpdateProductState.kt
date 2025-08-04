package com.example.eventplannerteam22.products.presentation.editproduct

data class UpdateProductState(
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