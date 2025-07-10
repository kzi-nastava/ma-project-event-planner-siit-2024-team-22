package com.example.eventplannerteam22.auth.registration

import androidx.compose.foundation.background
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
import com.example.eventplannerteam22.network.apiResultHandler
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
            apiResultHandler(
                onSuccess = {
                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.RegistrationScreen.route) { inclusive = true }
                    }
                },
                apiResult = result,
                logTag = LOG_TAG,
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