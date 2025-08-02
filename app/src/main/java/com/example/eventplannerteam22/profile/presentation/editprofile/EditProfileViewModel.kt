package com.example.eventplannerteam22.profile.presentation.editprofile

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.profile.data.ProfileRepository
import com.example.eventplannerteam22.profile.data.UpdateProfileRequest
import com.example.eventplannerteam22.profile.domen.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    val profileRepository: ProfileRepository,
) : ViewModel() {

    var screenState by mutableStateOf(EditProfileScreenState())
    private val resultChannel = Channel<ApiResult<Profile>>()
    val apiResults = resultChannel.receiveAsFlow()
    lateinit var oldProfile: Profile

    fun onEvent(event: EditProfileUIEvent) {
        when (event) {
            is EditProfileUIEvent.NameChanged -> {
                screenState = screenState.copy(
                    name = event.value,
                    nameErrorText = isNameValid(event.value)
                )
            }

            is EditProfileUIEvent.SurnameChanged -> {
                screenState = screenState.copy(
                    surname = event.value,
                    surnameErrorText = isNameValid(event.value)
                )
            }

            is EditProfileUIEvent.EmailChanged -> {
                screenState = screenState.copy(
                    email = event.value,
                    emailErrorText = isEmailValid(event.value)
                )
            }

            is EditProfileUIEvent.PhoneChanged -> {
                screenState = screenState.copy(
                    phone = event.value,
                    phoneErrorText = isPhoneValid(event.value)
                )
            }

            is EditProfileUIEvent.AddressChanged -> {
                screenState = screenState.copy(
                    address = event.value,
                )
            }

            is EditProfileUIEvent.PasswordChanged -> {
                screenState = screenState.copy(
                    password = event.value,
                    passwordErrorText = isPasswordCorrect(event.value)
                )
            }

            is EditProfileUIEvent.SubmitChanges -> {
                submitChanges(event.id)
            }
        }
    }

    private fun isNameValid(name: String): String? {
        return when {
            name.isBlank() -> "Name cannot be empty"
            name.any { !it.isLetter() } -> "Name can only contain letters"
            else -> null
        }
    }

    private fun isPhoneValid(phone: String): String? {
        return when {
            phone.any { !it.isDigit() } -> "Phone number can only contain digits"
            else -> null
        }
    }

    private fun isEmailValid(email: String): String? {
        return when {
            email.isBlank() -> "Email cannot be empty"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            else -> null
        }
    }

    private fun isAddressValid(address: String): String? {
        TODO()
    }

    private fun isPasswordCorrect(password: String): String? {
        TODO()
    }

    fun submitChanges(id: Int) {
        viewModelScope.launch {
            resultChannel.send(
                profileRepository.updateProfile(
                    id = id,
                    updateProfileRequest = UpdateProfileRequest(
                        name = screenState.name,
                        surname = screenState.surname,
                        email = screenState.email
                    )
                )
            )
        }
    }

    fun initScreenState(profile: Profile) {
        oldProfile = profile
        screenState = screenState.copy(
            name = profile.name,
            surname = profile.surname,
            email = profile.email,
            phone = profile.phone ?: "",
            address = profile.homeAddress ?: "",
        )
    }
}