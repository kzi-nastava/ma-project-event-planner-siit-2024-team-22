package com.example.eventplannerteam22.admin.reports.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eventplannerteam22.userreport.presentation.UserReport

@Composable
fun AdminReportModerationScreen(
    viewModel: AdminReportModerationViewModel = hiltViewModel(),
) {
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(viewModel.error) {
        viewModel.error?.let { error ->
            errorMessage = error
            showErrorDialog = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadPendingReports()
    }

    Scaffold { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (viewModel.reports.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(paddingValues.calculateTopPadding() + 16.dp))
                        Text("No pending reports")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    items(viewModel.reports) { report ->
                        AdminReportListItem(
                            report = report,
                            onApprove = { viewModel.updateReportStatus(report.id, "APPROVED") },
                            onReject = { viewModel.updateReportStatus(report.id, "REJECTED") },
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
fun AdminReportListItem(
    report: UserReport,
    onApprove: () -> Unit,
    onReject: () -> Unit,
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
            Text(
                text = "Report ID: ${report.id}",
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Reporter: User #${report.reporterId}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Reported User: User #${report.reportedUserId}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Report Text:",
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = report.text,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onApprove,
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Approve")
                }

                Button(
                    onClick = onReject,
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reject")
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                color = when (report.status) {
                    "APPROVED" -> MaterialTheme.colorScheme.primaryContainer
                    "REJECTED" -> MaterialTheme.colorScheme.errorContainer
                    else -> MaterialTheme.colorScheme.surfaceVariant
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Status: ${report.status}",
                    color = when (report.status) {
                        "APPROVED" -> MaterialTheme.colorScheme.onPrimaryContainer
                        "REJECTED" -> MaterialTheme.colorScheme.onErrorContainer
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
