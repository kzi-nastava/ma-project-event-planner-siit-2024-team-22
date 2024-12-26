package com.example.eventplannerteam22

import android.content.SharedPreferences
import android.util.Log
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
            val token = api.login(request = AuthRequest(email, password))
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

}