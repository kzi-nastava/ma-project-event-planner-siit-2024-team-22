package com.example.eventplannerteam22.auth.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.auth.AuthRepository
import com.example.eventplannerteam22.auth.AuthResult
import com.example.eventplannerteam22.auth.AuthState
import com.example.eventplannerteam22.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    var state by mutableStateOf(AuthState())
        private set

    private val resultChannel = Channel<ApiResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    fun onEvent(event: RegistrationUiEvent){
        when(event) {
            is RegistrationUiEvent.RegistrationNameChanged -> {
                state = state.copy(registrationName = event.value)
            }

            is RegistrationUiEvent.RegistrationSurnameChanged -> {
                state = state.copy(registrationSurname = event.value)
            }

            is RegistrationUiEvent.RegistrationEmailChanged -> {
                state = state.copy(registrationEmail = event.value)
            }

            is RegistrationUiEvent.RegistrationPasswordChanged -> {
                state = state.copy(registrationPassword = event.value)
            }

            is RegistrationUiEvent.RegistrationRoleChanged -> {
                state = state.copy(registrationRole = event.value)
            }

            is RegistrationUiEvent.Registration -> {
                registration()
            }
        }
    }

    private fun registration(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.register(
                state.registrationName,
                state.registrationSurname,
                state.registrationEmail,
                state.registrationPassword,
                state.registrationRole
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

}