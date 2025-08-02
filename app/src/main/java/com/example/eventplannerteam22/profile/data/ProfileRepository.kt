package com.example.eventplannerteam22.profile.data

import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.profile.domen.Profile

interface ProfileRepository {
    suspend fun getProfileById(id: Int): ApiResult<Profile>
    suspend fun getProfileByEmail(email: String): ApiResult<Profile>

    suspend fun updateProfile(
        id: Int,
        updateProfileRequest: UpdateProfileRequest
    ): ApiResult<Profile>
}