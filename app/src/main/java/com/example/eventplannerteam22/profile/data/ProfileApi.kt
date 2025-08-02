package com.example.eventplannerteam22.profile.data

import com.example.eventplannerteam22.profile.domen.Profile
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileApi {
    @GET("/profiles/{id}")
    suspend fun getProfileById(@Path("id") id: Int): Profile

    @GET("/profiles/email?email={email}")                                   // not sure if this is correct
    suspend fun getProfileByEmail(@Path("email") email: String): Profile

    @PUT("/profiles/{id}")
    suspend fun updateProfile(
        @Path("id") id: Int,
        @Body updateProfileRequest: UpdateProfileRequest
    ): Profile
}