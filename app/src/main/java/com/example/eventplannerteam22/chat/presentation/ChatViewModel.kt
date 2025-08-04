package com.example.eventplannerteam22.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.chat.data.ChatRepository
import com.example.eventplannerteam22.chat.domen.ChatMessage
import com.example.eventplannerteam22.chat.domen.MessageType
import com.example.eventplannerteam22.session.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _connectionStatus = MutableStateFlow(false)
    val connectionStatus: StateFlow<Boolean> = _connectionStatus

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var currentConversationId: String? = null

    init {
        connectWebSocket()
        observeMessages()
    }

    private fun connectWebSocket() {
        repository.connectWebSocket()
        _connectionStatus.value = true
    }

    private fun observeMessages() {
        viewModelScope.launch {
            repository.observeMessages().collect { message ->
                _messages.value = _messages.value + message
            }
        }
    }

    fun sendMessage(content: String, receiverId: Int, conversationId: String) {
        currentConversationId = conversationId
        val currentUserId = sessionRepository.getUserId()
        if (currentUserId == -1) {
            _error.value = "User not authenticated"
            return
        }

        val message = ChatMessage(
            content = content,
            messageType = MessageType.CHAT,
            senderId = currentUserId,
            receiverId = receiverId
        )

        repository.sendMessage(message, conversationId)
    }

    fun joinConversation(conversationId: String) {
        currentConversationId = conversationId
        repository.subscribeToConversation(conversationId)
    }

    fun leaveConversation() {
        currentConversationId?.let {
            repository.unsubscribeFromConversation(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.disconnectWebSocket()
    }
}