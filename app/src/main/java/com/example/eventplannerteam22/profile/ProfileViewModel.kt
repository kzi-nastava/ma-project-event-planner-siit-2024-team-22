package com.example.eventplannerteam22.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.auth0.jwt.JWT
import com.example.eventplannerteam22.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
) : ViewModel() {
    private val logTag = "ProfileViewModel"
    var userProfile by mutableStateOf<Profile?>(null)
        private set

    suspend fun loadUserProfile(accessToken: String) {
        val userId = JWT.decode(accessToken).getClaim("userId").asInt()
        val result = profileRepository.getProfileById(userId)
        when (result) {
            is ApiResult.Success -> {
                userProfile = result.data
            }

            else -> Log.e(logTag, "Some kind of error happened")
        }
    }

    fun unloadUserProfile() {
        userProfile = null
    }
}