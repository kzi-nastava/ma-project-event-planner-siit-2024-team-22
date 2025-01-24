package com.example.eventplannerteam22.auth

interface AuthRepository{
    suspend fun register(name: String, surname: String,email : String, password: String, role: String) : AuthResult<Unit>
    suspend fun login(email : String, password: String) : AuthResult<Unit>
    suspend fun auth() : AuthResult<Unit>


}