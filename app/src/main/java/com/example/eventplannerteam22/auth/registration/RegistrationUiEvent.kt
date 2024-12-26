package com.example.eventplannerteam22.auth.registration

sealed class RegistrationUiEvent {
    data class RegistrationNameChanged(val value: String): RegistrationUiEvent()
    data class RegistrationSurnameChanged(val value: String): RegistrationUiEvent()
    data class RegistrationEmailChanged(val value: String): RegistrationUiEvent()
    data class RegistrationPasswordChanged(val value: String): RegistrationUiEvent()
    object Registration: RegistrationUiEvent()
}