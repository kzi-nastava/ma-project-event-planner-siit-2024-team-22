package com.example.eventplannerteam22.booking.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eventplannerteam22.booking.domain.model.Booking

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

@Composable
fun BookingListScreen(
    bookings: List<Booking>,
    userId: Int = -1,
    bookingViewModel: BookingViewModel = hiltViewModel(),
    bookingFormViewModel: BookingFormViewModel = hiltViewModel(),
) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Booking")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (bookings.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(paddingValues.calculateTopPadding() + 16.dp))
                        Text("No bookings yet")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 80.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(paddingValues.calculateTopPadding() + 16.dp))
                    }
                    items(bookings) { booking ->
                        BookingListItem(booking)
                    }
                }
            }
            if (bookingViewModel.error != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = paddingValues.calculateTopPadding() + 8.dp)
                ) {
                    Surface(color = MaterialTheme.colorScheme.error, shadowElevation = 4.dp) {
                        Text(
                            text = bookingViewModel.error ?: "",
                            color = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
        if (showDialog) {
            val events by bookingFormViewModel.events.collectAsState()
            val services by bookingFormViewModel.services.collectAsState()
            LaunchedEffect(showDialog) {
                if (showDialog) {
                    bookingFormViewModel.loadEvents()
                    bookingFormViewModel.loadServices()
                }
            }
            CreateBookingDialog(
                userId = userId,
                eventOptions = events.map { it.id to it.name },
                serviceOptions = services.map { it.id to it.name },
                onCreate = { eventId, serviceId, startDate, endDate ->
                    bookingViewModel.createBooking(
                        com.example.eventplannerteam22.booking.data.remote.CreateBookingRequest(
                            userId = userId,
                            eventId = eventId,
                            solutionId = serviceId,
                            startDate = startDate,
                            endDate = endDate
                        ),
                        onSuccess = { showDialog = false }
                    )
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}

@Composable
fun BookingListItem(booking: Booking) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(top = 24.dp, bottom = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Event: ${booking.event.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Service: ${booking.services.joinToString { it.name }}")
            Text(text = "Start: ${booking.startDate}")
            Text(text = "End: ${booking.endDate}")
            Text(text = if (booking.confirmed) "Confirmed" else "Not confirmed", color = if (booking.confirmed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
        }
    }
}
