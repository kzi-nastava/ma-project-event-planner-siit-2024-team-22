package com.example.eventplannerteam22.auth

import android.content.SharedPreferences
import android.util.Log
import com.example.eventplannerteam22.auth.login.LoginRequest
import com.example.eventplannerteam22.auth.registration.RegistrationRequest
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val prefs: SharedPreferences
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): AuthResult<Unit> {
        return try {
            val token = api.login(request = LoginRequest(email, password))
            prefs.edit().putString("jwt", "Bearer $token")
            AuthResult.Authorized()
        } catch (e : HttpException){
            if(e.code() == 401){
                AuthResult.Unauthorized()
            } else{
                Log.e("AuthRepositoryImpl", e.message.toString())
                AuthResult.UnknownError()
            }
        } catch (e : Exception){
            Log.e("AuthRepositoryImpl", e.message.toString())
            AuthResult.UnknownError()
        }
    }

    override suspend fun auth(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
            api.auth("Bearer $token")
            AuthResult.Authorized()
        } catch (e : HttpException){
            if(e.code() == 401){
                AuthResult.Unauthorized()
            } else{
                AuthResult.UnknownError()
            }
        } catch (e : Exception){
            AuthResult.UnknownError()
        }
    }

    override suspend fun register(
        name: String,
        surname: String,
        email: String,
        password: String
    ): AuthResult<Unit> {
        return try {
            api.register(request = RegistrationRequest(name, surname, email, password))
            login(email,password)
        } catch (e : HttpException){
            if(e.code() == 401){
                AuthResult.Unauthorized()
            } else{
                Log.e("AuthRepositoryImpl", e.message.toString())
                AuthResult.UnknownError()
            }
        } catch (e : Exception){
            Log.e("AuthRepositoryImpl", e.message.toString())
            AuthResult.UnknownError()
        }
    }

}