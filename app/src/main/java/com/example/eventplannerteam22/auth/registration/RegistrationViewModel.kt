package com.example.eventplannerteam22.auth.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.auth.AuthRepository
import com.example.eventplannerteam22.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    var registrationScreenState by mutableStateOf(RegistrationScreenState())
        private set

    private val resultChannel = Channel<ApiResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    fun onEvent(event: RegistrationUiEvent) {
        when (event) {
            is RegistrationUiEvent.RegistrationNameChanged -> {
                registrationScreenState =
                    registrationScreenState.copy(
                        name = event.value,
                        nameErrorText = validateName(event.value)
                    )
            }

            is RegistrationUiEvent.RegistrationSurnameChanged -> {
                registrationScreenState =
                    registrationScreenState.copy(
                        surname = event.value,
                        surnameErrorText = validateSurname(event.value)
                    )
            }

            is RegistrationUiEvent.RegistrationEmailChanged -> {
                registrationScreenState =
                    registrationScreenState.copy(
                        email = event.value,
                        emailErrorText = validateEmail(event.value)
                    )
            }

            is RegistrationUiEvent.RegistrationPasswordChanged -> {
                registrationScreenState =
                    registrationScreenState.copy(
                        password = event.value,
                        passwordErrorText = validatePassword(event.value)
                    )
            }

            is RegistrationUiEvent.RegistrationRoleChanged -> {
                registrationScreenState =
                    registrationScreenState.copy(role = event.value)
            }

            is RegistrationUiEvent.Registration -> {
                if (validateAll()) registration()
            }
        }
    }

    private fun registration() {
        viewModelScope.launch {
            registrationScreenState = registrationScreenState.copy(isLoading = true)
            val result = repository.register(
                registrationScreenState.name,
                registrationScreenState.surname,
                registrationScreenState.email,
                registrationScreenState.password,
                registrationScreenState.role
            )
            resultChannel.send(result)
            registrationScreenState = registrationScreenState.copy(isLoading = false)
        }
    }

    private fun validateName(name: String): String? =
        when {
            name.isBlank() -> "Name cannot be empty"
            name.any { !it.isLetter() } -> "Name can only contain letters"
            else -> null
        }

    private fun validateSurname(surname: String): String? =
        when {
            surname.isBlank() -> "Name cannot be empty"
            surname.any { !it.isLetter() } -> "Name can only contain letters"
            else -> null
        }

    private fun validateEmail(email: String): String? =
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            "Invalid email address"
        else null

    private fun validatePassword(password: String): String? =
        when {
            password.any { it.isWhitespace() } -> "No whitespaces allowed in password"
            password.length < 6 -> "Password must be at least 6 characters"
            password.none { it.isDigit() } -> "Password must contain at least one digit"
            password.none { it in """!@#${'$'}%^&*()-_=+[]{};:'\",.<>?/""" }
                -> """Password should contain at least one of these special characters !@#${'$'}%^&*()-_=+[]{};:'\",.<>?/"""

            else -> null
        }

    private fun validateAll(): Boolean {
        val nameError = validateName(registrationScreenState.name)
        val surnameError = validateSurname(registrationScreenState.surname)
        val emailError = validateEmail(registrationScreenState.email)
        val passwordError = validatePassword(registrationScreenState.password)

        registrationScreenState = registrationScreenState.copy(
            nameErrorText = nameError,
            surnameErrorText = surnameError,
            emailErrorText = emailError,
            passwordErrorText = passwordError,
        )

        return listOf(
            nameError,
            surnameError,
            emailError,
            passwordError,
        ).all { it == null }
    }
}