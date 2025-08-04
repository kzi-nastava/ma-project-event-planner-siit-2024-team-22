package com.example.eventplannerteam22.solutions.comments.data

import com.example.eventplannerteam22.solutions.comments.domain.SolutionComment

import javax.inject.Inject

class SolutionCommentRepository @Inject constructor(
    private val api: SolutionCommentApi
) {
    suspend fun getComments(solutionId: Int): List<SolutionComment> {
        return api.getComments(solutionId)
    }

    suspend fun addComment(token: String, userId: Int, solutionId: Int, text: String): SolutionComment {
        return api.addComment("Bearer $token", CreateSolutionCommentRequest(userId, solutionId, text))
    }
}