package com.example.eventplannerteam22.events.invite.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eventplannerteam22.session.SessionViewModel
import com.example.eventplannerteam22.events.presentation.eventdetails.EventDetailsViewModel


@Composable
fun InviteEmailSection(
    eventId: Int,
    organizerId: Int,
    sessionViewModel: SessionViewModel,
    viewModel: EventDetailsViewModel
) {
    val session by sessionViewModel.session.collectAsState()
    var input by remember { mutableStateOf("") }
    val result by viewModel.inviteResult.collectAsState()

    if (session.loggedIn && session.userId == organizerId) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.medium)
                .padding(8.dp)
        ) {
            Text("Invite users by email", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.small)
                    .padding(12.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (input.isBlank()) {
                            Text(
                                text = "Enter emails separated by commas",
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                            )
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val emails = input.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    if (emails.isNotEmpty() && session.accessToken != null) {
                        viewModel.sendInvites(session.accessToken!!, eventId, emails)
                        input = ""
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Send Invites")
            }

            result?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(it, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}