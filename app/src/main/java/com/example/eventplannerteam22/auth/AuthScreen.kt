package com.example.eventplannerteam22.auth

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.auth.login.LoginUiEvent
import com.example.eventplannerteam22.auth.login.LoginViewModel
import com.example.eventplannerteam22.auth.registration.RegistrationUiEvent
import com.example.eventplannerteam22.auth.registration.RegistrationViewModel
import com.example.eventplannerteam22.auth.registration.UserRole
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.apiResultHandler
import com.example.eventplannerteam22.router.Screen
import com.example.eventplannerteam22.session.SessionViewModel

@Composable
fun AuthScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    loginViewModel: LoginViewModel = hiltViewModel(),
    registrationViewModel: RegistrationViewModel = hiltViewModel(),
    sessionViewModel: SessionViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    var isLogin by remember { mutableStateOf(true) }
    val loginState = loginViewModel.state
    val registrationState = registrationViewModel.registrationScreenState
    val context = LocalContext.current

    LaunchedEffect(loginViewModel, context) {
        loginViewModel.apiResults.collect { result ->
            apiResultHandler(
                onSuccess = {
                    if (result is ApiResult.Success) {
                        sessionViewModel.login(result.data)
                        navController.navigate(Screen.MainScreen.route) {
                            popUpTo(Screen.LoginScreen.route) { inclusive = true }
                        }
                    }
                },
                apiResult = result,
                logTag = "AuthScreen-Login",
                context = context,
                unauthorizedErrorText = "Invalid email or password"
            )
        }
    }

    LaunchedEffect(registrationViewModel, context) {
        registrationViewModel.authResults.collect { result ->
            apiResultHandler(
                onSuccess = {
                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.RegistrationScreen.route) { inclusive = true }
                    }
                },
                apiResult = result,
                logTag = "AuthScreen-Register",
                context = context,
                conflictErrorText = "User with this email already exists"
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLogin) {
            // Login Fields
            ValidatingInputTextField(
                label = "Email",
                value = loginState.email,
                onValueChange = { loginViewModel.onEvent(LoginUiEvent.EmailChanged(it)) },
                isError = loginState.emailErrorText != null,
                errorText = loginState.emailErrorText
            )
            Spacer(modifier = Modifier.height(16.dp))

            ValidatingInputTextField(
                label = "Password",
                value = loginState.password,
                onValueChange = { loginViewModel.onEvent(LoginUiEvent.PasswordChanged(it)) },
                isError = loginState.passwordErrorText != null,
                errorText = loginState.passwordErrorText
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { loginViewModel.onEvent(LoginUiEvent.Login) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Sign in")
            }
        } else {
            ValidatingInputTextField(
                label = "Name",
                value = registrationState.name,
                onValueChange = {
                    registrationViewModel.onEvent(
                        RegistrationUiEvent.RegistrationNameChanged(
                            it
                        )
                    )
                },
                isError = registrationState.nameErrorText != null,
                errorText = registrationState.nameErrorText
            )
            Spacer(modifier = Modifier.height(16.dp))

            ValidatingInputTextField(
                label = "Surname",
                value = registrationState.surname,
                onValueChange = {
                    registrationViewModel.onEvent(
                        RegistrationUiEvent.RegistrationSurnameChanged(
                            it
                        )
                    )
                },
                isError = registrationState.surnameErrorText != null,
                errorText = registrationState.surnameErrorText
            )
            Spacer(modifier = Modifier.height(16.dp))

            ValidatingInputTextField(
                label = "Email",
                value = registrationState.email,
                onValueChange = {
                    registrationViewModel.onEvent(
                        RegistrationUiEvent.RegistrationEmailChanged(
                            it
                        )
                    )
                },
                isError = registrationState.emailErrorText != null,
                errorText = registrationState.emailErrorText
            )
            Spacer(modifier = Modifier.height(16.dp))

            ValidatingInputTextField(
                label = "Password",
                value = registrationState.password,
                onValueChange = {
                    registrationViewModel.onEvent(
                        RegistrationUiEvent.RegistrationPasswordChanged(
                            it
                        )
                    )
                },
                isError = registrationState.passwordErrorText != null,
                errorText = registrationState.passwordErrorText
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Role Dropdown
            var expanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(registrationState.role)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    UserRole.entries.forEach { role ->
                        DropdownMenuItem(
                            text = { Text(role.name) },
                            onClick = {
                                registrationViewModel.onEvent(
                                    RegistrationUiEvent.RegistrationRoleChanged(
                                        role.name
                                    )
                                )
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { registrationViewModel.onEvent(RegistrationUiEvent.Registration) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Register")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { isLogin = !isLogin }) {
            Text(if (isLogin) "Don't have an account? Register" else "Already have an account? Sign in")
        }
    }

    // Loading Indicator
    if (loginState.isLoading || registrationState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
