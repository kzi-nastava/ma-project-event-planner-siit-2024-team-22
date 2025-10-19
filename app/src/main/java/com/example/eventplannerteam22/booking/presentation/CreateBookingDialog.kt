package com.example.eventplannerteam22.booking.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateBookingDialog(
    userId: Int,
    eventOptions: List<Pair<Int, String>>,
    serviceOptions: List<Pair<Int, String>>,
    onCreate: (eventId: Int, serviceId: Int, startTime: String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedEventId by remember { mutableStateOf<Int?>(null) }
    var selectedServiceId by remember { mutableStateOf<Int?>(null) }
    var startTime by remember { mutableStateOf("10:00:00") }
    var eventMenuExpanded by remember { mutableStateOf(false) }
    var serviceMenuExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create Booking") },
        text = {
            Column {
                Text("Event:")
                OutlinedButton(onClick = { eventMenuExpanded = true }) {
                    Text(eventOptions.find { it.first == selectedEventId }?.second ?: "Select event")
                }
                DropdownMenu(expanded = eventMenuExpanded, onDismissRequest = { eventMenuExpanded = false }) {
                    eventOptions.forEach { (id, name) ->
                        DropdownMenuItem(
                            text = { Text(name) },
                            onClick = {
                                selectedEventId = id
                                eventMenuExpanded = false
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                
                Text("Service:")
                OutlinedButton(onClick = { serviceMenuExpanded = true }) {
                    Text(serviceOptions.find { it.first == selectedServiceId }?.second ?: "Select service")
                }
                DropdownMenu(expanded = serviceMenuExpanded, onDismissRequest = { serviceMenuExpanded = false }) {
                    serviceOptions.forEach { (id, name) ->
                        DropdownMenuItem(
                            text = { Text(name) },
                            onClick = {
                                selectedServiceId = id
                                serviceMenuExpanded = false
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = startTime,
                    onValueChange = { startTime = it },
                    label = { Text("Start time (HH:mm:ss)") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedEventId != null && selectedServiceId != null && startTime.isNotBlank()) {
                        onCreate(selectedEventId!!, selectedServiceId!!, startTime)
                    }
                },
                enabled = selectedEventId != null && selectedServiceId != null && startTime.isNotBlank()
            ) { Text("Create") }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
