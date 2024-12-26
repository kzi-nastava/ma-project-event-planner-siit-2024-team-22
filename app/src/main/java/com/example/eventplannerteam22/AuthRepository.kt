package com.example.eventplannerteam22

import android.content.SharedPreferences

interface AuthRepository{
//    suspend fun register(email : String, password: String) : AuthResult<Unit>
    suspend fun login(email : String, password: String) : AuthResult<Unit>
    suspend fun auth() : AuthResult<Unit>

}