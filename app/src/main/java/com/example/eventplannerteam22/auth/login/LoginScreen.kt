package com.example.eventplannerteam22.auth.login

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.router.Screen
import com.example.eventplannerteam22.session.SessionViewModel

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
                    sessionViewModel.updateState(
                        accessToken = result.data.accessToken,
                        refreshToken = result.data.refreshToken,
                        expiresIn = result.data.expiresIn,
                        loggedIn = true
                    )
                    navController.navigate(Screen.MainScreen.route)
                }

                is ApiResult.Unauthorized -> {
                    Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }

                is ApiResult.ServerError -> {
                    Toast.makeText(context, "Server error occurred!", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(
                        context,
                        "Something is completely wrong here",
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

@Composable
fun ValidatingInputTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorText: String?
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(errorText ?: "")
            }
        }
    )
}