package com.example.eventplannerteam22.notifications

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.eventplannerteam22.router.Screen

private const val LOG_TAG = "NotificationPermission"

@Composable
fun NotificationPermissionScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context as ComponentActivity

    val permissionRequested = remember { mutableStateOf(false) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            Log.d(LOG_TAG, "Initial notification permission granted: $granted")
            navController.navigate(Screen.Auth.route) {
                popUpTo(Screen.NotificationPermission.route) { inclusive = true }
            }
        }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !permissionRequested.value) {
            permissionRequested.value = true
            val permissionStatus = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            )
            if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                Log.d(LOG_TAG, "Permission already granted")
                navController.navigate(Screen.Auth.route) {
                    popUpTo(Screen.NotificationPermission.route) { inclusive = true }
                }
            }
        } else {
            navController.navigate(Screen.Auth.route) {
                popUpTo(Screen.NotificationPermission.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Загрузка...")
    }
}