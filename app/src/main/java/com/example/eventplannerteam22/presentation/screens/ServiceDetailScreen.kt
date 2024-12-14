package com.example.eventplannerteam22.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventplannerteam22.presentation.viewmodel.ServiceViewModel


@Composable
fun ServiceDetail(serviceId: String?) {
    val viewModel: ServiceViewModel = viewModel()
    val service = viewModel.getServiceById(serviceId)

    if (service == null) {
        ErrorScreen()
    } else {
        ServiceDetailContent(service=service)
    }
}

@Composable
fun ServiceDetailContent(service: Solution) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(text = "Service Detail", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 16.dp))
        Text(text = "ID: ${service.id}", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp))
        Text(text = "Name: ${service.name}", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp))
        Text(text = "Description: ${service.description}", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun ErrorScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(text = "Service not found", style = MaterialTheme.typography.bodyLarge)
    }
}