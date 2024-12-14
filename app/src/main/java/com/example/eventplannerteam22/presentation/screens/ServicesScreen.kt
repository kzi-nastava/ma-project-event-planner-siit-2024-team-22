package com.example.eventplannerteam22.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.presentation.viewmodel.ServiceViewModel

data class Solution(
    val id: String,
    val name: String,
    val description: String
)

@Composable
fun ServiceList(services: List<Solution>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(services) { service ->
            ServiceItem(service, navController)
        }
    }
}

@Composable
fun ServiceItem(service: Solution, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable {
                navController.navigate(
                    "service_detail_screen/${service.id}"
                )
            }
        ,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${service.name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Email: ${service.description}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun ServicesScreen(navController: NavController){
    val viewModel: ServiceViewModel = viewModel()
    val services = viewModel.getAllServices()
    ServiceList(services=services, navController=navController)
}

//    Text("ServicesScreen")
//    CreateService(navController)