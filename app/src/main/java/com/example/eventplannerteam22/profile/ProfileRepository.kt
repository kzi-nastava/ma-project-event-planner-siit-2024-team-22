package com.example.eventplannerteam22.profile

import com.example.eventplannerteam22.network.ApiResult

interface ProfileRepository {
    suspend fun getProfileById(id: Int): ApiResult<Profile>
    suspend fun getProfileByEmail(email: String): ApiResult<Profile>
}