package com.example.eventplannerteam22.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.eventplannerteam22.router.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Navigate to MainScreen after delay
    LaunchedEffect(Unit) {
        delay(2000) // 2 seconds delay
        navController.navigate(Screen.MainScreen.route) {
            popUpTo(Screen.SplashScreen.route) { inclusive = true } // Clear splash screen from back stack
        }
    }

    // Splash Screen UI
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primaryContainer // Use theme's primary container color
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Welcome to Event Planner",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer, // Text color based on primary container
                textAlign = TextAlign.Center
            )
        }
    }
}
