
package com.example.eventplannerteam22.userreport.presentation

import androidx.compose.ui.Alignment

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun UserReportScreen(
    navController: NavController,
    reporterId: Int,
    viewModel: UserReportViewModel = hiltViewModel()
) {
    var reportedEmail by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var success by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 48.dp)
    ) {
        Text(text = "Report user", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = reportedEmail,
                onValueChange = { reportedEmail = it },
                label = { Text("User email") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Report text") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                isLoading = true
                error = null
                success = false
                viewModel.submitReportByEmail(
                    reporterId = reporterId,
                    reportedUserEmail = reportedEmail,
                    text = text,
                    onSuccess = {
                        isLoading = false
                        success = true
                    },
                    onError = {
                        isLoading = false
                        error = it
                    }
                )
            },
            enabled = !isLoading && reportedEmail.isNotBlank() && text.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoading) "Sending..." else "Submit Report")
        }
        if (error != null) {
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }
        if (success) {
            Text("Report sent!", color = MaterialTheme.colorScheme.primary)
        }
    }
}
