package com.example.eventplannerteam22.auth

import com.example.eventplannerteam22.auth.login.LoginRequest
import com.example.eventplannerteam22.auth.registration.RegistrationRequest
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.safeApiCall
import okhttp3.OkHttpClient
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val okHttpClient: OkHttpClient
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): ApiResult<TokenResponse> {
        return safeApiCall(okHttpClient) {
            api.login(LoginRequest(email, password))
        }
    }

    override suspend fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        role: String
    ): ApiResult<Unit> {
        return safeApiCall(okHttpClient) {
            api.register(
                RegistrationRequest(
                    name,
                    surname,
                    email,
                    password,
                    role
                )
            )
        }
    }

}