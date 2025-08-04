package com.example.eventplannerteam22.chat.presentation

// ConversationsScreen.kt
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ConversationsScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onConversationClick: (String, Int) -> Unit
) {
    val messages by viewModel.messages.collectAsState()
    val connectionStatus by viewModel.connectionStatus.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = if (connectionStatus) "Connected" else "Disconnected")

        // List of conversations would go here
        // For now showing all messages as example
        LazyColumn {
            items(messages) { message ->
                // This would be a conversation item in a real implementation
                Text(text = "Message from ${message.senderUsername}: ${message.content}")
            }
        }
    }
}