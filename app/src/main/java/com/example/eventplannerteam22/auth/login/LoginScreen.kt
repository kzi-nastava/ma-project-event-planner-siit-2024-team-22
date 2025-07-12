package com.example.eventplannerteam22.auth.login

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.auth.ValidatingInputTextField
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.apiResultHandler
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
    val session = sessionViewModel.session.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(loginViewModel, context) {
        loginViewModel.apiResults.collect { result ->
            apiResultHandler(
                onSuccess = {
                    when (result) {
                        is ApiResult.Success -> {
                            sessionViewModel.login(result.data)
                            navController.navigate(Screen.MainScreen.route) {
                                popUpTo(Screen.LoginScreen.route) { inclusive = true }
                            }
                        }

                        else -> Unit
                    }

                },
                apiResult = result,
                logTag = LOG_TAG,
                context = context,
                unauthorizedErrorText = "Invalid email or password"
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
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}