package com.example.eventplannerteam22.chat.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventplannerteam22.chat.domen.ChatMessage

@Composable
fun ChatScreen(
    conversationId: String,
    receiverId: Int,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsState()
    val connectionStatus by viewModel.connectionStatus.collectAsState()
    val error by viewModel.error.collectAsState()

    var messageText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(conversationId) {
        viewModel.joinConversation(conversationId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Connection status indicator
        Text(
            text = if (connectionStatus) "Connected" else "Disconnected",
            modifier = Modifier.padding(8.dp)
        )

        // Error message
        error?.let { err ->
            Text(
                text = err,
                modifier = Modifier.padding(8.dp)
            )
        }

        // Messages list
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages) { message ->
                MessageItem(message = message)
            }
        }

        // Message input
        OutlinedTextField(
            value = messageText,
            onValueChange = { messageText = it },
            modifier = Modifier.padding(8.dp),
            placeholder = { Text("Type a message") },
            singleLine = false,
            maxLines = 3,
            keyboardActions = KeyboardActions(
                onSend = {
                    if (messageText.isNotBlank()) {
                        viewModel.sendMessage(messageText, receiverId, conversationId)
                        messageText = ""
                        keyboardController?.hide()
                    }
                }
            )
        )
    }
}

@Composable
fun MessageItem(message: ChatMessage) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = message.senderUsername ?: "Unknown")
        Text(text = message.content)
        message.messageDeliveryStatusEnum?.let { status ->
            Text(text = status.name)
        }
    }
}