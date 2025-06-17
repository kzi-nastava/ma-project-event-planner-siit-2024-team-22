package com.example.eventplannerteam22.auth

import com.example.eventplannerteam22.network.ApiResult

interface AuthRepository{
    suspend fun register(name: String, surname: String,email : String, password: String, role: String) : ApiResult<Unit>
    suspend fun login(email : String, password: String) : ApiResult<Unit>
}