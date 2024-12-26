package com.example.eventplannerteam22.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.auth.AuthRepository
import com.example.eventplannerteam22.auth.AuthResult
import com.example.eventplannerteam22.auth.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    // Declare state using 'by' delegation
    var state by mutableStateOf(AuthState())
        private set // Make it immutable from outside the ViewModel

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    init{
        auth()
    }

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.LoginEmailChanged -> {
                state = state.copy(loginEmail = event.value)
            }
            is LoginUiEvent.LoginPasswordChanged -> {
                state = state.copy(loginPassword = event.value)
            }
            is LoginUiEvent.Login -> {
                // Handle sign-in logic here (e.g., call repository)
                login()
            }
        }
    }

    private fun login() {
        viewModelScope.launch{
            state = state.copy(isLoading = true)
            val result = repository.login(state.loginEmail, state.loginPassword)
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun auth(){
        viewModelScope.launch{
            state = state.copy(isLoading = true)
            val result = repository.auth()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}
