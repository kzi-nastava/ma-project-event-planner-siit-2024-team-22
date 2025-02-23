package com.example.eventplannerteam22.events

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun AddEventScreen(
    navController: NavController,
    viewModel: AddEventViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var maxCapacity by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var dateOfEvent by remember { mutableStateOf("") }
    var selectedEventTypeId by remember { mutableStateOf<Int?>(null) }
    var selectedSolutionId by remember { mutableStateOf<Int?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    LaunchedEffect(viewModel) {
        viewModel.createEventResult.collect { success ->
            if (success) {
                Toast.makeText(context, "Event Created Successfully", Toast.LENGTH_LONG).show()
                navController.popBackStack()
            } else {
                Toast.makeText(context, "Failed to create event", Toast.LENGTH_LONG).show()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Event Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = selectedEventTypeId?.toString() ?: "",
                onValueChange = { selectedEventTypeId = it.toIntOrNull() },
                label = { Text("Event Type ID") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = maxCapacity,
                onValueChange = { maxCapacity = it },
                label = { Text("Max Capacity") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = dateOfEvent,
                onValueChange = { dateOfEvent = it },
                label = { Text("Date (YYYY-MM-DDTHH:MM:SS)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = selectedSolutionId?.toString() ?: "",
                onValueChange = { selectedSolutionId = it.toIntOrNull() },
                label = { Text("Solution ID") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.name = name
                    viewModel.description = description
                    viewModel.selectedEventTypeId = selectedEventTypeId
                    viewModel.maxCapacity = maxCapacity
                    viewModel.location = location
                    viewModel.dateOfEvent = dateOfEvent
                    viewModel.selectedSolutionId = selectedSolutionId

                    scope.launch {
                        viewModel.createEvent()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Event")
            }
        }
    }
}