package com.example.eventplannerteam22.products.comments.domain

data class ProductComment(
    val id: Int,
    val text: String,
    val authorName: String,
    val authorSurname: String,
    val productId: Int
) {
    val authorFullName: String
        get() = "$authorName $authorSurname"
}