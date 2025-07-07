package com.example.eventplannerteam22.auth.login

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.eventplannerteam22.session.SessionViewModel

const val LOG_TAG = "LoginScreen"

@Composable
fun LoginScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    loginViewModel: LoginViewModel = hiltViewModel(),
    sessionViewModel: SessionViewModel = hiltViewModel(LocalContext.current as ComponentActivity)

) {
    val loginScreenState = loginViewModel.state
    val context = LocalContext.current
    LaunchedEffect(loginViewModel, context) {
        loginViewModel.apiResults.collect { result ->
            when (result) {
                is ApiResult.Success -> {
                    Log.w(LOG_TAG, "Successful login: ${result.data}")
                    sessionViewModel.login(result.data)
                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.LoginScreen.route) { inclusive = true }
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
                    Toast.makeText(context, "Conflict occurred", Toast.LENGTH_SHORT).show()
                }

                is ApiResult.ServerError -> {
                    Log.e(LOG_TAG, "ServerError ${result.code}: ${result.message}")
                    Toast.makeText(context, "Server error (${result.code})", Toast.LENGTH_SHORT)
                        .show()
                }

                is ApiResult.UnknownError -> {
                    Log.e(LOG_TAG, "UnknownError ${result.code}: ${result.message}")
                    Toast.makeText(context, "Unexpected error (${result.code})", Toast.LENGTH_SHORT)
                        .show()
                }

                is ApiResult.ConnectionError -> {
                    Log.e(LOG_TAG, "ConnectionError: ${result.message}")
                    Toast.makeText(
                        context,
                        "Connection error: (${result.message})",
                        Toast.LENGTH_SHORT
                    )
                        .show()
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
            label = "Email",
            value = loginScreenState.email,
            onValueChange = { input -> loginViewModel.onEvent(LoginUiEvent.EmailChanged(input)) },
            isError = loginScreenState.emailErrorText != null,
            errorText = loginScreenState.emailErrorText
        )

        Spacer(modifier = Modifier.height(16.dp))

        ValidatingInputTextField(
            label = "Password",
            value = loginScreenState.password,
            onValueChange = { input -> loginViewModel.onEvent(LoginUiEvent.PasswordChanged(input)) },
            isError = loginScreenState.passwordErrorText != null,
            errorText = loginScreenState.passwordErrorText
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                loginViewModel.onEvent(LoginUiEvent.Login)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Sign in")
        }
    }

    if (loginScreenState.isLoading) {
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