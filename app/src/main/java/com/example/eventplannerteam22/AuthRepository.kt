package com.example.eventplannerteam22

interface AuthRepository {
//    suspend fun register(email : String, password: String) : AuthResult<Unit>
    suspend fun login(email : String, password: String) : AuthResult<Unit>
    suspend fun auth() : AuthResult<Unit>

}