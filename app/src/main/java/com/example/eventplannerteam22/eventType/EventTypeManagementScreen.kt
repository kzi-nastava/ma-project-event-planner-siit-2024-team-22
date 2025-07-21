package com.example.eventplannerteam22.eventType

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.network.apiResultHandler
import com.example.eventplannerteam22.router.Screen

const val LOG_TAG = "EventTypeManagementScreen"

@Composable
fun EventTypeManagementScreen(
    eventTypeManagementViewModel: EventTypeManagementViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    navController: NavController
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        eventTypeManagementViewModel.fetchEventTypes()
    }

    LaunchedEffect(eventTypeManagementViewModel.loadEventTypesResults) {
        eventTypeManagementViewModel.loadEventTypesResults.collect { result ->
            apiResultHandler(
                apiResult = result,
                logTag = LOG_TAG,
                context = context,
                onSuccess = { eventTypeManagementViewModel.loadEventTypes(result) }
            )
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(eventTypeManagementViewModel.eventTypes) { eventType ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(Screen.MainScreen.route)
                    }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = eventType.name,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = eventType.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (eventType.active) "visible" else "hidden",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}