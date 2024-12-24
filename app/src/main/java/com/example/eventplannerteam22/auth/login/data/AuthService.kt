package com.example.eventplannerteam22.data

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}