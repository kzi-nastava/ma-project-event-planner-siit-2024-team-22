package com.example.eventplannerteam22.auth.login.data

import com.example.eventplannerteam22.auth.login.domen.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}