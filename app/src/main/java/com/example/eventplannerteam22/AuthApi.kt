package com.example.eventplannerteam22

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
//    @POST("/auth/register")
//    suspend fun register(
//        @Body request: AuthRequest
//    )

    @POST("/auth/login")
    suspend fun login(
        @Body request: AuthRequest
    ) : TokenResponse

    @GET("/auth")
    suspend fun auth(
        @Header("Authorization") token: String
    )
}