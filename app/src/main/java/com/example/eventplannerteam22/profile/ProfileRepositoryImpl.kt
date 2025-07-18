package com.example.eventplannerteam22.profile

import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.safeApiCall
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi,
) : ProfileRepository {
    override suspend fun getProfileById(id: Int): ApiResult<Profile> {
        return safeApiCall { api.getProfileById(id) }
    }

    override suspend fun getProfileByEmail(email: String): ApiResult<Profile> {
        return safeApiCall { api.getProfileByEmail(email) }
    }

    override suspend fun updateProfile(
        id: Int,
        updateProfileRequest: UpdateProfileRequest
    ): ApiResult<Profile> {
        return safeApiCall {
            api.updateProfile(
                id = id,
                updateProfileRequest = updateProfileRequest
            )
        }
    }
}