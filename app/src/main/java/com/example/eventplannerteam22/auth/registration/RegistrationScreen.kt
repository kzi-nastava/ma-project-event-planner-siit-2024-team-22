package com.example.eventplannerteam22.auth.registration

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.auth.ValidatingInputTextField
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.router.Screen

const val LOG_TAG = "RegistrationScreen"

@Composable
fun RegistrationScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val registrationScreenState = viewModel.registrationScreenState
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            when (result) {
                is ApiResult.Success -> {
                    Log.w(LOG_TAG, "Successful registration: ${result.data}")
                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.RegistrationScreen.route) {
                            inclusive = true
                        }
                    }
                }

                is ApiResult.BadRequest -> {
                    Log.w(LOG_TAG, "BadRequest: ${result.message}")
                    Toast.makeText(context, result.message ?: "Bad request", Toast.LENGTH_SHORT)
                        .show()
                }

                is ApiResult.Unauthorized -> {
                    Log.w(LOG_TAG, "Unauthorized: ${result.message}")
                    Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }

                is ApiResult.Forbidden -> {
                    Log.w(LOG_TAG, "Forbidden: ${result.message}")
                    Toast.makeText(context, "Access denied", Toast.LENGTH_SHORT).show()
                }

                is ApiResult.NotFound -> {
                    Log.w(LOG_TAG, "NotFound: ${result.message}")
                    Toast.makeText(context, "Resource not found", Toast.LENGTH_SHORT).show()
                }

                is ApiResult.Conflict -> {
                    Log.w(LOG_TAG, "Conflict: ${result.message}")
                    Toast.makeText(
                        context,
                        "User with this email already exists",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ApiResult.ServerError -> {
                    Log.e(LOG_TAG, "ServerError ${result.code}: ${result.message}")
                    Toast.makeText(
                        context,
                        "Server error occurred! ${result.code}, ${result.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ApiResult.UnknownError -> {
                    Log.e(LOG_TAG, "UnknownError ${result.code}: ${result.message}")
                    Toast.makeText(
                        context,
                        "Unexpected error (${result.code})",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ApiResult.ConnectionError -> {
                    Log.e(LOG_TAG, "ConnectionError: ${result.message}")
                    Toast.makeText(
                        context,
                        "Connection error: ${result.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ValidatingInputTextField(
            label = "Name",
            value = registrationScreenState.name,
            onValueChange = { input ->
                viewModel.onEvent(
                    RegistrationUiEvent.RegistrationNameChanged(
                        input
                    )
                )
            },
            isError = registrationScreenState.nameErrorText != null,
            errorText = registrationScreenState.nameErrorText
        )

        Spacer(modifier = Modifier.height(16.dp))

        ValidatingInputTextField(
            label = "Surname",
            value = registrationScreenState.surname,
            onValueChange = { input ->
                viewModel.onEvent(
                    RegistrationUiEvent.RegistrationSurnameChanged(
                        input
                    )
                )
            },
            isError = registrationScreenState.surnameErrorText != null,
            errorText = registrationScreenState.surnameErrorText
        )

        Spacer(modifier = Modifier.height(16.dp))

        ValidatingInputTextField(
            label = "Email",
            value = registrationScreenState.email,
            onValueChange = { input ->
                viewModel.onEvent(
                    RegistrationUiEvent.RegistrationEmailChanged(
                        input
                    )
                )
            },
            isError = registrationScreenState.emailErrorText != null,
            errorText = registrationScreenState.emailErrorText
        )

        Spacer(modifier = Modifier.height(16.dp))

        ValidatingInputTextField(
            label = "Password",
            value = registrationScreenState.password,
            onValueChange = { input ->
                viewModel.onEvent(
                    RegistrationUiEvent.RegistrationPasswordChanged(
                        input
                    )
                )
            },
            isError = registrationScreenState.passwordErrorText != null,
            errorText = registrationScreenState.passwordErrorText
        )

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = registrationScreenState.role)

            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                UserRole.values().forEach { role ->
                    DropdownMenuItem(
                        text = { Text(text = role.name) },
                        onClick = {
                            viewModel.onEvent(RegistrationUiEvent.RegistrationRoleChanged(role.name))
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.onEvent(RegistrationUiEvent.Registration)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Register")
        }
    }
    if (registrationScreenState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}