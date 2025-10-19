package com.example.eventplannerteam22.userreport.presentation

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import com.squareup.moshi.JsonClass

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

@JsonClass(generateAdapter = true)
data class UserReport(
    val id: Int,
    val reporterId: Int,
    val reportedUserId: Int,
    val text: String,
    val status: String,
    val reporter: UserDto? = null,
    val reportedUser: UserDto? = null
)

interface UserReportApi {
    @GET("/profiles/email")
    suspend fun getUserByEmail(@Query("email") email: String): UserDto

    @POST("/reports")
    suspend fun submitReport(@Body request: CreateUserReportRequest)

    @GET("/reports/pending")
    suspend fun getPendingReports(): List<UserReport>

    @PATCH("/reports/{reportId}/status")
    suspend fun updateReportStatus(@Path("reportId") reportId: Int, @Query("status") status: String): UserReport
}