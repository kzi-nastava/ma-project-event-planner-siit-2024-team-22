package com.example.eventplannerteam22.auth.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun LoginScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: LoginScreenViewModel = viewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var hasNavigated by remember { mutableStateOf(false) }

    val loginState by viewModel.loginState.collectAsState()

    Column(modifier = Modifier.padding(paddingValues)) {
        TextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("Username") },
            label = { Text("Username") }
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password") },
            label = { Text("Password") }
        )

        Button(onClick = {
            viewModel.login(username, password)
        }) {
            Text("Login")
        }

        when (loginState) {
            is LoginScreenState.Idle -> Text("Please enter your credentials.")
            is LoginScreenState.Loading -> Text("Logging in...")
            is LoginScreenState.Success -> {
                Text("Login successful!")
//                if (!hasNavigated) { // Navigate only if not already navigated
//                    navController.navigate(Screen.MainScreen.route)
//                    hasNavigated = true
//                }
            }
            is LoginScreenState.Error -> {
                val errorMessage = (loginState as LoginScreenState.Error).message
                Text("Error: $errorMessage")
            }
        }
    }
}

