package com.example.eventplannerteam22.solutions.comments.domain

data class SolutionComment(
    val id: Int,
    val text: String,
    val authorName: String,
    val authorSurname: String,
    val solutionId: Int
) {
    val authorFullName: String
        get() = "$authorName $authorSurname"
}