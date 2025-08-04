package com.example.eventplannerteam22.solutions.comments.data

import com.example.eventplannerteam22.solutions.comments.domain.SolutionComment
import retrofit2.http.*

data class CreateSolutionCommentRequest(
    val userId: Int,
    val solutionId: Int,
    val text: String
)

interface SolutionCommentApi {
    @GET("/solutions/comments/solution/{solutionId}/approved")
    suspend fun getComments(@Path("solutionId") solutionId: Int): List<SolutionComment>

    @POST("/solutions/comments")
    suspend fun addComment(
        @Header("Authorization") token: String,
        @Body comment: CreateSolutionCommentRequest
    ): SolutionComment
}