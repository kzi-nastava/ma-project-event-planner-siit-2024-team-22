package com.example.eventplannerteam22.solutions.comments.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventplannerteam22.session.SessionViewModel
import com.example.eventplannerteam22.solutions.comments.domain.SolutionComment

@Composable
fun SolutionCommentCard(comment: SolutionComment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = comment.authorFullName,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun SolutionCommentSection(
    solutionId: Int,
    sessionViewModel: SessionViewModel,
    viewModel: SolutionCommentViewModel = hiltViewModel()
) {
    val comments = viewModel.comments.collectAsState()
    val error = viewModel.error.collectAsState()
    val session by sessionViewModel.session.collectAsState()

    var input by remember { mutableStateOf("") }

    LaunchedEffect(solutionId) {
        viewModel.loadComments(solutionId)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Comments", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        comments.value.forEach {
            SolutionCommentCard(comment = it)
            Spacer(modifier = Modifier.height(4.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (session.loggedIn && session.userId != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(8.dp)
            ) {
                BasicTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(12.dp),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.background,
                                    shape = MaterialTheme.shapes.small
                                )
                                .padding(8.dp)
                        ) {
                            if (input.isBlank()) {
                                Text(
                                    text = "Enter your comment...",
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (input.isNotBlank() && session.accessToken != null) {
                            viewModel.addComment(session.accessToken, session.userId!!, solutionId, input)
                            input = ""
                        }
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Add Comment")
                }
            }
        } else {
            Text(
                "Log in to leave a comment",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if (error.value != null) {
            Text("Error: ${error.value}", color = MaterialTheme.colorScheme.error)
        }
    }
}