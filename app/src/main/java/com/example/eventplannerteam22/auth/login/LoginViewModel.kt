package com.example.eventplannerteam22.auth.login

import android.util.Patterns
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
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val resultChannel = Channel<ApiResult<Unit>>()
    val apiResults = resultChannel.receiveAsFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> {
                state = state.copy(
                    email = event.value,
                    emailErrorText = isValidEmail(event.value)
                )
            }
            is LoginUiEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.value,
                    passwordErrorText = isValidPassword(event.value)
                )
            }
            is LoginUiEvent.Login -> {
                if (
                    isValidEmail(state.email)==null &&
                    isValidPassword(state.password)==null
                    ) login()
            }
        }
    }

    private fun login() {
        viewModelScope.launch{
            state = state.copy(isLoading = true)
            val result = repository.login(state.email, state.password)
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun isValidEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email cannot be empty"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            else -> null
        }
    }

    private fun isValidPassword(password: String): String? {
        return when {
            password.isBlank() -> "Password cannot be empty"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
    }
}

