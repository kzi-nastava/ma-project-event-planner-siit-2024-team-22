package com.example.eventplannerteam22.profile

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(

) : ViewModel() {

    var screenState by mutableStateOf(EditProfileScreenState())
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
                TODO()
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

    fun submitChanges() {
        TODO()
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