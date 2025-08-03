package com.example.eventplannerteam22.admin.comments.data.repository

import com.example.eventplannerteam22.admin.comments.data.api.AdminCommentApi
import com.example.eventplannerteam22.admin.comments.domain.AdminComment
import javax.inject.Inject

class AdminCommentRepository @Inject constructor(
    private val api: AdminCommentApi
) {
    suspend fun getUnapproved(type: String): List<AdminComment> = when (type) {
        "product" -> api.getUnapprovedProductComments()
        "solution" -> api.getUnapprovedSolutionComments()
        "event" -> api.getUnapprovedEventComments()
        else -> emptyList()
    }

    suspend fun approveComment(type: String, id: Int): AdminComment = when (type) {
        "product" -> api.approveProductComment(id)
        "solution" -> api.approveSolutionComment(id)
        "event" -> api.approveEventComment(id)
        else -> throw IllegalArgumentException("Unknown comment type: $type")
    }
}