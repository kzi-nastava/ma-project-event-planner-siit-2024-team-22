package com.example.eventplannerteam22.auth

import android.content.SharedPreferences
import com.example.eventplannerteam22.auth.login.LoginRequest
import com.example.eventplannerteam22.auth.registration.RegistrationRequest
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.safeApiCall
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val prefs: SharedPreferences
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): ApiResult<Unit>{
        return safeApiCall {
            val token = api.login(LoginRequest(email, password))
            prefs.edit()
                .putString("jwt-access", "Bearer ${token.accessToken}")
                .putString("jwt-refresh", "Bearer ${token.refreshToken}")
                .putLong("jwt-expiresIn", token.expiresIn)
                .apply()
        }
    }

    override suspend fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        role: String
    ): ApiResult<Unit> {
       return safeApiCall { api.register(RegistrationRequest(name, surname, email, password, role)) }
    }

}