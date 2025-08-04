package com.example.eventplannerteam22.auth.login

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.auth0.jwt.JWT
import com.example.eventplannerteam22.auth.ValidatingInputTextField
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.apiResultHandler
import com.example.eventplannerteam22.notifications.NotificationSseService
import com.example.eventplannerteam22.router.Screen
import com.example.eventplannerteam22.session.SessionViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.eventplannerteam22.R

const val LOG_TAG = "LoginScreen"


fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "sse_channel",
            "SSE Уведомления",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Канал для push-уведомлений через SSE"
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

@Composable
fun LoginScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    loginViewModel: LoginViewModel = hiltViewModel(),
    sessionViewModel: SessionViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val loginScreenState = loginViewModel.state
    val context = LocalContext.current
    val activity = context as ComponentActivity

    var sseStarted by remember { mutableStateOf(false) }


    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            Log.d(LOG_TAG, "Permission result: $granted")

            if (granted) {
                val userId = sessionViewModel.session.value.accessToken?.let {
                    JWT.decode(it).getClaim("userId").asInt()
                }
                if (userId != null && !sseStarted) {
                    Log.d(LOG_TAG, "Permission granted — starting SSE")
                    NotificationSseService(context, userId).start()
                    sseStarted = true
                }
            } else {
                Log.w(LOG_TAG, "Permission denied. Cannot show notifications.")
            }
        }
    )

    LaunchedEffect(loginViewModel) {
        loginViewModel.apiResults.collect { result ->
            apiResultHandler(
                onSuccess = {
                    if (result is ApiResult.Success) {
                        sessionViewModel.login(result.data)

                        val accessToken = result.data.accessToken
                        val userId = JWT.decode(accessToken).getClaim("userId").asInt()

                        Log.d(LOG_TAG, "Login success! SDK = ${Build.VERSION.SDK_INT}")

                        createNotificationChannel(context)
                        // Тестовое уведомление (можно убрать)
                        NotificationCompat.Builder(context, "sse_channel")
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle("Тестовое уведомление")
                            .setContentText("Проверка работает ли канал")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .build()
                            .also {
                                (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(1, it)
                            }

                        // Проверка разрешения и запуск SSE
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            val permissionStatus = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            )
                            Log.d(LOG_TAG, "Permission status = $permissionStatus")

                            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                                Log.d(LOG_TAG, "Permission already granted — starting SSE")
                                NotificationSseService(context, userId).start()
                                sseStarted = true
                            } else {
                                Log.d(LOG_TAG, "Requesting permission...")
                                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                        } else {
                            Log.d(LOG_TAG, "No permission needed on this Android version — starting SSE")
                            NotificationSseService(context, userId).start()
                            sseStarted = true
                        }

                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
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
            onClick = { loginViewModel.onEvent(LoginUiEvent.Login) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Sign in")
        }
    }

    if (loginScreenState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}