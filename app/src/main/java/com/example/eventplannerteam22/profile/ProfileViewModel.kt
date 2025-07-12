package com.example.eventplannerteam22.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.auth0.jwt.JWT
import com.example.eventplannerteam22.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
) : ViewModel() {
    private val logTag = "ProfileViewModel"
    var userProfile by mutableStateOf(Profile())
        private set

    var screenState by mutableStateOf(ProfileScreenState())
        private set

    private val resultChannel = Channel<ApiResult<Profile>>()
    val apiResults = resultChannel.receiveAsFlow()

    suspend fun loadUserProfile(accessToken: String) {
        screenState.isLoading = true
        val userId = JWT.decode(accessToken).getClaim("userId").asInt()
        val result = profileRepository.getProfileById(userId)
        resultChannel.send(result)
        if (result is ApiResult.Success) {
            userProfile = result.data
            screenState.isLoading = false
        }
    }

    fun updateUserProfile(
        id: Int?,
        name: String?,
        surname: String?,
        email: String?,
        password: String?,
        phone: String?,
        homeAddress: String?,
        role: String?
    ) {
        userProfile = userProfile.copy(
            id = id ?: userProfile.id,
            name = name ?: userProfile.name,
            surname = surname ?: userProfile.surname,
            email = email ?: userProfile.email,
            password = password ?: userProfile.password,
            phone = phone ?: userProfile.phone,
            homeAddress = homeAddress ?: userProfile.homeAddress,
            role = role ?: userProfile.role,
        )
    }

    fun unloadUserProfile() {
        userProfile = Profile()
    }
}