package com.example.eventplannerteam22.events.presentation.eventlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.events.domen.EventListItem
import com.example.eventplannerteam22.network.apiResultHandler
import com.example.eventplannerteam22.router.Screen
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun EventListScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    eventListViewModel: EventListViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val events = eventListViewModel.screenState.events

    LaunchedEffect(Unit) {
        eventListViewModel.fetchEvents()
    }

    LaunchedEffect(eventListViewModel) {
        eventListViewModel.fetchResults.collect { result ->
            apiResultHandler(
                apiResult = result,
                logTag = "EventListScreen",
                context = context,
                onSuccess = { eventListViewModel.loadEvents(result) }
            )
        }
    }
    EventListContent(
        events, paddingValues,
        onAddProductClick = { navController.navigate(Screen.CreateEvent.route) },
        onEventClick = { eventId -> navController.navigate(Screen.EventDetails.createRoute(eventId)) }
    )
}

@Composable
fun EventListContent(events: List<EventListItem>, paddingValues: PaddingValues, onAddProductClick: () -> Unit, onEventClick: (Int) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = "Events",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )
                HorizontalDivider()
            }
            items(events) { event ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clickable { onEventClick(event.id) },
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(event.name, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("By ${event.organizer} • ${event.location}", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Type: ${event.eventTypeName}", style = MaterialTheme.typography.bodySmall)
                        Text(
                            "Date: ${event.eventDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(event.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { onAddProductClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Product")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventListPreview() {
    val paddingValues = PaddingValues(20.dp)
    val events = listOf(
        EventListItem(
            id = 1,
            name = "Tech Conference 2025",
            description = "A conference about the future of tech.",
            eventTypeName = "Conference",
            location = "Belgrade",
            organizer = "TechWorld",
            eventDate = LocalDate.of(2025, 9, 10)
        ),
        EventListItem(
            id = 2,
            name = "Jazz Night",
            description = "Live jazz music in the city park.",
            eventTypeName = "Concert",
            location = "Novi Sad",
            organizer = "City Music Org",
            eventDate = LocalDate.of(2025, 8, 20)
        )
    )
    EventListContent(events, paddingValues, {}, {})
}