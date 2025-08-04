package com.example.eventplannerteam22.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.chat.data.ChatRepository
import com.example.eventplannerteam22.session.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val _conversations = MutableStateFlow<List<ConversationUiModel>>(emptyList())
    val conversations: StateFlow<List<ConversationUiModel>> = _conversations

    private val _uiState = MutableStateFlow(ChatListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadConversations()
    }

    fun loadConversations() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val conversations = chatRepository.getConversations()
                val currentUserId = sessionRepository.getUserId()

                val uiModels = conversations.map { conversation ->
                    val otherParticipantId = if (conversation.participant1Id == currentUserId) {
                        conversation.participant2Id
                    } else {
                        conversation.participant1Id
                    }

                    ConversationUiModel(
                        id = conversation.id,
                        otherParticipantId = otherParticipantId,
                        otherParticipantName = "User $otherParticipantId", // Replace with actual name lookup
                        lastMessagePreview = conversation.lastMessage ?: "",
                        unreadCount = 0 // You'll need to implement this
                    )
                }

                _uiState.update {
                    it.copy(
                        conversations = uiModels,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: IOException) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load conversations"
                    )
                }
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    500 -> "Server error occurred"
                    401 -> "Unauthorized - please login again"
                    else -> "HTTP error: ${e.message}"
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = errorMessage
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error: ${e.message}"
                    )
                }
            }
        }

        fun refresh() {
            loadConversations()
        }
    }
}

data class ConversationUiModel(
    val id: String,
    val otherParticipantId: Int,
    val otherParticipantName: String,
    val lastMessagePreview: String,
    val unreadCount: Int
)

data class ChatListUiState(
    val conversations: List<ConversationUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)