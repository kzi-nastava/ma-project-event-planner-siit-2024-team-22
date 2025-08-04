package com.example.eventplannerteam22.chat.data

// ChatRepository.kt
import com.example.eventplannerteam22.chat.domen.ChatMessage
import com.example.eventplannerteam22.chat.domen.Conversation
import com.example.eventplannerteam22.session.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val chatWebSocketService: ChatWebSocketService,
    private val chatApi: ChatApi,
    private val sessionRepository: SessionRepository

) {
    fun connectWebSocket() = chatWebSocketService.connect()
    fun disconnectWebSocket() = chatWebSocketService.disconnect()

    fun observeMessages(): Flow<ChatMessage> = chatWebSocketService.messageFlow

    fun sendMessage(message: ChatMessage, conversationId: String) {
        chatWebSocketService.sendMessage(message, conversationId)
    }

    fun subscribeToConversation(conversationId: String) {
        chatWebSocketService.subscribeToConversation(conversationId)
    }

    fun unsubscribeFromConversation(conversationId: String) {
        chatWebSocketService.unsubscribeFromConversation(conversationId)
    }

    suspend fun getConversations(): List<Conversation> {
        val userId = sessionRepository.getUserId()
        return chatApi.getConversations(userId).map { response ->
            Conversation(
                id = response.id,
                participant1Id = response.participant1Id,
                participant2Id = response.participant2Id,
                lastMessage = response.lastMessage,
                lastMessageTime = response.lastMessageTime
            )
        }
    }
}