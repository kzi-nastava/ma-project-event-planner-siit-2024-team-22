package com.example.eventplannerteam22.auth.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.auth.login.data.RetrofitInstance
import com.example.eventplannerteam22.auth.login.domen.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<LoginScreenState>(LoginScreenState.Idle)
    val loginState: StateFlow<LoginScreenState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginScreenState.Loading
            try {
                val response = RetrofitInstance.api.login(LoginRequest(email, password))
                _loginState.value = LoginScreenState.Success(response.token)
            } catch (e: Exception) {
                _loginState.value = LoginScreenState.Error(e.message ?: "An error occurred")
            }
        }
    }
}
