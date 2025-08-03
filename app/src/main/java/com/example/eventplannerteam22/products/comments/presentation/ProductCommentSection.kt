package com.example.eventplannerteam22.products.comments.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.auth0.jwt.JWT
import com.example.eventplannerteam22.session.SessionViewModel
import com.example.eventplannerteam22.products.comments.domain.ProductComment



@Composable
fun CommentCard(comment: ProductComment) {
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
fun ProductCommentSection(
    productId: Int,
    sessionViewModel: SessionViewModel,
    viewModel: ProductCommentViewModel = hiltViewModel()
) {
    val comments = viewModel.comments.collectAsState()
    val error = viewModel.error.collectAsState()
    val session by sessionViewModel.session.collectAsState()

    var input by remember { mutableStateOf("") }

    LaunchedEffect(productId) {
        viewModel.loadComments(productId)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Comments", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        comments.value.forEach {
            CommentCard(comment = it)
            Spacer(modifier = Modifier.height(4.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))
//
//        Text(
//            text = "DEBUG: loggedIn=${session.loggedIn}, userId=${session.userId}, token=${session.accessToken.take(10)}",
//            style = MaterialTheme.typography.bodySmall,
//            color = MaterialTheme.colorScheme.primary
//        )


        if (session.loggedIn && session.userId != null) {
            BasicTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    if (input.isNotBlank()) {
                        viewModel.addComment(session.userId!!, productId, input)
                        input = ""
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Add Comment")
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