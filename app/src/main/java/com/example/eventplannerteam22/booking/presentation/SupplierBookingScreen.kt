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

@Composable
fun SupplierBookingScreen(
    supplierId: Int = -1,
    viewModel: SupplierBookingViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(viewModel.error) {
        viewModel.error?.let { error ->
            errorMessage = error
            showErrorDialog = true
        }
    }

    LaunchedEffect(Unit) {
        if (supplierId != -1) {
            viewModel.loadSupplierBookings(supplierId)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (viewModel.bookings.isEmpty()) {
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
                        .padding(vertical = paddingValues.calculateTopPadding())
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    items(viewModel.bookings) { booking ->
                        SupplierBookingListItem(
                            booking = booking,
                            onConfirm = { viewModel.confirmBooking(booking.id) },
                            isLoading = viewModel.isLoading
                        )
                    }
                }
            }
        }

        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { 
                    showErrorDialog = false
                    viewModel.clearError()
                },
                title = { Text("Error") },
                text = { Text(errorMessage) },
                confirmButton = {
                    Button(
                        onClick = {
                            showErrorDialog = false
                            viewModel.clearError()
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Composable
fun SupplierBookingListItem(
    booking: Booking,
    onConfirm: () -> Unit,
    isLoading: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp, bottom = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Event: ${booking.event.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Organizer: ${booking.user.name}")
            Text(text = "Service: ${booking.services.joinToString { it.name }}")
            Text(text = "Date: ${booking.bookingDate}")
            Text(text = "Time: ${booking.startTime} - ${booking.endTime}")
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Status Badge
                val statusText = if (booking.confirmed) "CONFIRMED" else "PENDING"
                Surface(
                    color = if (booking.confirmed) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = statusText,
                        color = if (booking.confirmed) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                
                // Confirm Button (only show if not confirmed)
                if (!booking.confirmed) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onConfirm,
                        enabled = !isLoading,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}
