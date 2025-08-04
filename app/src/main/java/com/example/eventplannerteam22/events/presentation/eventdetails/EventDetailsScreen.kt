package com.example.eventplannerteam22.events.presentation.eventdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.R
import com.example.eventplannerteam22.events.comments.presentation.EventCommentSection
import com.example.eventplannerteam22.network.apiResultHandler
import com.example.eventplannerteam22.session.SessionViewModel
import java.time.format.DateTimeFormatter

@Composable
fun EventDetailScreen(
    paddingValues: PaddingValues,
    eventId: Int,
    navController: NavController,
    sessionViewModel: SessionViewModel,
    viewModel: EventDetailsViewModel = hiltViewModel()
) {
    val event by viewModel.event.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchResult.collect { result ->
            apiResultHandler(
                apiResult = result,
                logTag = "EventDetailScreen",
                context = context,
                onSuccess = {}
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadEvent(eventId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
    ) {
        event?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sample_image),
                    contentDescription = "Event Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = it.name, style = MaterialTheme.typography.headlineSmall)
                    Text(text = "Type: ${it.eventType.name}", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = "Date: ${it.eventDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(text = "Location: ${it.location}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Private: ${if (it.isPrivate) "Yes" else "No"}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Max Capacity: ${it.maxCapacity}", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = "Organizer: ${it.user.name + " " + it.user.surname}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(text = it.description, style = MaterialTheme.typography.bodyMedium)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.medium)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Activities:", style = MaterialTheme.typography.titleMedium)
                    if (it.eventActivities.isEmpty()) {
                        Text("No activities added.")
                    } else {
                        it.eventActivities.forEach { activity ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.small)
                                    .padding(12.dp)
                            ) {
                                Text(text = activity.name, style = MaterialTheme.typography.titleSmall)
                                Text(
                                    text = "From: ${activity.startTime.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "To: ${activity.endTime.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "Location: ${activity.location}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.medium)
                        .padding(16.dp)
                ) {
                    EventCommentSection(
                        eventId = eventId,
                        sessionViewModel = sessionViewModel
                    )
                }

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text("Back")
                }
            }
        }
    }
}
