package com.example.eventplannerteam22.admin.comments.domain

data class AdminComment(
    val id: Int,
    val text: String,
    val authorName: String,
    val authorSurname: String,
) {
    val authorFullName: String
        get() = "$authorName $authorSurname"
}
