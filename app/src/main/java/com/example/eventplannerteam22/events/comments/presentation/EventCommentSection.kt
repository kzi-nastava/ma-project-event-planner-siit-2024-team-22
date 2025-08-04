package com.example.eventplannerteam22.events.comments.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventplannerteam22.events.comments.domain.EventComment
import com.example.eventplannerteam22.session.SessionViewModel

@Composable
fun EventCommentCard(comment: EventComment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = comment.authorFullName,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = comment.text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun EventCommentSection(
    eventId: Int,
    sessionViewModel: SessionViewModel,
    viewModel: EventCommentViewModel = hiltViewModel()
) {
    val comments by viewModel.comments.collectAsState()
    val error by viewModel.error.collectAsState()
    val session by sessionViewModel.session.collectAsState()
    var input by remember { mutableStateOf("") }

    LaunchedEffect(eventId) {
        viewModel.loadComments(eventId)
    }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Comments", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        comments.forEach {
            EventCommentCard(comment = it)
            Spacer(modifier = Modifier.height(4.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (session.loggedIn && session.userId != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.medium)
                    .padding(8.dp)
            ) {
                BasicTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.small)
                        .padding(12.dp),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    decorationBox = { innerTextField ->
                        Box(Modifier.padding(8.dp)) {
                            if (input.isBlank()) {
                                Text("Enter your comment...", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (input.isNotBlank()) {
                            viewModel.addComment(session.accessToken!!, session.userId!!, eventId, input)
                            input = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Add Comment")
                }
            }
        } else {
            Text("Log in to leave a comment", style = MaterialTheme.typography.bodySmall)
        }

        error?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
        }
    }
}