package com.example.eventplannerteam22.admin.comments.data.api

import com.example.eventplannerteam22.admin.comments.domain.AdminComment
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface AdminCommentApi {
    @GET("/products/comments/unapproved")
    suspend fun getUnapprovedProductComments(): List<AdminComment>

    @GET("/solutions/comments/unapproved")
    suspend fun getUnapprovedSolutionComments(): List<AdminComment>

    @GET("/events/comments/unapproved")
    suspend fun getUnapprovedEventComments(): List<AdminComment>

    @PUT("/products/comments/approve/{id}")
    suspend fun approveProductComment(@Path("id") id: Int): AdminComment

    @PUT("/solutions/comments/approve/{id}")
    suspend fun approveSolutionComment(@Path("id") id: Int): AdminComment

    @PUT("/events/comments/approve/{id}")
    suspend fun approveEventComment(@Path("id") id: Int): AdminComment
}