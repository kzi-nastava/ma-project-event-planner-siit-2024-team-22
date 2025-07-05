package com.example.eventplannerteam22.auth.registration

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.eventplannerteam22.auth.AuthResult
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.router.Screen


@Composable
fun RegistrationScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            when (result) {
                is ApiResult.Success -> {
                    navController.navigate(Screen.MainScreen.route)
                }

                is ApiResult.Conflict -> {
                    Toast.makeText(context, "User with this email already exists", Toast.LENGTH_SHORT).show()
                }

                is ApiResult.ServerError -> {
                    Toast.makeText(context, "Server error occurred! ${result.code}, ${result.message}", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context, "Something is really really bad here", Toast.LENGTH_SHORT).show()
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
        TextField(
            value = state.registrationName,
            onValueChange = {
                viewModel.onEvent(RegistrationUiEvent.RegistrationNameChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Name")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.registrationSurname,
            onValueChange = {
                viewModel.onEvent(RegistrationUiEvent.RegistrationSurnameChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Surname")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.registrationEmail,
            onValueChange = {
                viewModel.onEvent(RegistrationUiEvent.RegistrationEmailChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Email")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.registrationPassword,
            onValueChange = {
                viewModel.onEvent(RegistrationUiEvent.RegistrationPasswordChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Password")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = state.registrationRole ?: "User")

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
    if (state.isLoading) {
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