package com.example.eventplannerteam22.profile

sealed class EditProfileUIEvent {
    data class NameChanged(val value: String) : EditProfileUIEvent()
    data class SurnameChanged(val value: String) : EditProfileUIEvent()
    data class PhoneChanged(val value: String) : EditProfileUIEvent()
    data class EmailChanged(val value: String) : EditProfileUIEvent()
    data class AddressChanged(val value: String) : EditProfileUIEvent()
    data class PasswordChanged(val value: String) : EditProfileUIEvent()
    object SubmitChanges : EditProfileUIEvent()
}