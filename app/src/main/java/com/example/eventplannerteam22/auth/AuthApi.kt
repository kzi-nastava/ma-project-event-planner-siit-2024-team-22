package com.example.eventplannerteam22.auth

import com.example.eventplannerteam22.auth.login.LoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
//    @POST("/auth/register")
//    suspend fun register(
//        @Body request: AuthRequest
//    )

    @POST("/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ) : TokenResponse

    @GET("/auth")
    suspend fun auth(
        @Header("Authorization") token: String
    )
}