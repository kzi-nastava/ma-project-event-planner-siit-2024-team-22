package com.example.eventplannerteam22.userreport.presentation

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class CreateUserReportRequest(
    val reporterId: Int,
    val reportedUserId: Int,
    val text: String
)

data class UserDto(
    val id: Int,
    val name: String?,
    val surname: String?,
    val email: String?
)

interface UserReportApi {
    @GET("/profiles/email")
    suspend fun getUserByEmail(@Query("email") email: String): UserDto

    @POST("/reports")
    suspend fun submitReport(@Body request: CreateUserReportRequest)
}