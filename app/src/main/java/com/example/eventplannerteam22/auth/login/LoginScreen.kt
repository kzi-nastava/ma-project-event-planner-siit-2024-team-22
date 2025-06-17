package com.example.eventplannerteam22.auth.login

import android.widget.Toast
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

@Composable
fun LoginScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
//            when(result) {
//                is AuthResult.Authorized -> {
//                    navController.navigate(Screen.MainScreen.route)
//                }
//                is AuthResult.Unauthorized -> {
//                    Toast.makeText(
//                        context,
//                        "You're not authorized",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//                is AuthResult.UnknownError -> {
//                    Toast.makeText(
//                        context,
//                        "An unknown error occurred",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
            when (result){
                is ApiResult.Success -> {navController.navigate(Screen.MainScreen.route)}
                is ApiResult.UnknownError -> {
                    Toast.makeText(context, "Sorry, Unknown error occurred!", Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Unauthorized -> {
                    Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
                is ApiResult.ServerError -> {
                    Toast.makeText(context, "Server error occurred!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context, "Something is completely wrong here", Toast.LENGTH_SHORT).show()
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
            value = state.loginEmail,
            onValueChange = {
                viewModel.onEvent(LoginUiEvent.LoginEmailChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Username")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.loginPassword,
            onValueChange = {
                viewModel.onEvent(LoginUiEvent.LoginPasswordChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Password")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.onEvent(LoginUiEvent.Login)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Sign in")
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