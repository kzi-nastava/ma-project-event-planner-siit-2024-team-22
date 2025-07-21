package com.example.eventplannerteam22.eventType

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.R
import com.example.eventplannerteam22.network.apiResultHandler
import com.example.eventplannerteam22.router.Screen

const val LOG_TAG = "EventTypeManagementScreen"

@Composable
fun EventTypesScreen(
    eventTypesViewModel: EventTypesViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    navController: NavController
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        eventTypesViewModel.fetchEventTypes()
    }

    LaunchedEffect(eventTypesViewModel.loadEventTypesResults) {
        eventTypesViewModel.loadEventTypesResults.collect { result ->
            apiResultHandler(
                apiResult = result,
                logTag = LOG_TAG,
                context = context,
                onSuccess = { eventTypesViewModel.loadEventTypes(result) }
            )
        }
    }
    Box(
        modifier = Modifier.padding(paddingValues)
    ) {
        Column {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Event types",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(3.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(3.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(eventTypesViewModel.eventTypes) { eventType ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        onClick = {

                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = eventType.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = eventType.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (eventType.active) "Status: Visible" else "Status: Hidden",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Switch(
                                checked = eventType.active,
                                onCheckedChange = {
                                    // TODO: add the hide action logic
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Recommended solution categories:",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            LazyRow {
                                items(eventType.solutionCategories) { solutionCategory ->
                                    Card(
                                        modifier = Modifier.padding(end = 8.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 12.dp, vertical = 6.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = solutionCategory.name,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate(Screen.CreateEventType.route) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.add_24px),
                contentDescription = "Create event type"
            )
        }
    }
}