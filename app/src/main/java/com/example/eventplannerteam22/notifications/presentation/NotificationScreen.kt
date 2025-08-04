package com.example.eventplannerteam22.notifications.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventplannerteam22.session.SessionViewModel
import com.example.eventplannerteam22.router.DrawerContent
import com.example.eventplannerteam22.presentation.MainLayout
import kotlinx.coroutines.CoroutineScope
import androidx.navigation.NavController
import androidx.compose.material3.DrawerState
import java.time.format.DateTimeFormatter

@Composable
fun NotificationScreen(
    navController: NavController,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    viewModel: NotificationViewModel = hiltViewModel()
) {
    MainLayout(
        navController = navController,
        drawerState = drawerState,
        coroutineScope = coroutineScope,
        drawerContent = {
            DrawerContent(navController, coroutineScope, drawerState)
        }
    ) { paddingValues ->
        NotificationContent(paddingValues, sessionViewModel, viewModel)
    }
}

@Composable
fun NotificationContent(
    paddingValues: PaddingValues,
    sessionViewModel: SessionViewModel,
    viewModel: NotificationViewModel
) {
    val session by sessionViewModel.session.collectAsState()
    val notifications by viewModel.notifications
    val error by viewModel.error

    LaunchedEffect(session.userId) {
        if (session.loggedIn && session.userId != null) {
            viewModel.loadNotifications(session.userId!!)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Text(
            text = "Notifications",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (error != null) {
            Text("Error: $error", color = MaterialTheme.colorScheme.error)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(notifications) { notification ->
                    NotificationCard(
                        message = notification.message,
                        timestamp = notification.timestamp
                            .format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun NotificationCard(message: String, timestamp: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = message, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = timestamp,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}