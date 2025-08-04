package com.example.eventplannerteam22.events.comments.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.events.comments.data.EventCommentRepository
import com.example.eventplannerteam22.events.comments.domain.EventComment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventCommentViewModel @Inject constructor(
    private val repository: EventCommentRepository
) : ViewModel() {

    private val _comments = MutableStateFlow<List<EventComment>>(emptyList())
    val comments: StateFlow<List<EventComment>> = _comments

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadComments(eventId: Int) {
        viewModelScope.launch {
            try {
                _comments.value = repository.getComments(eventId)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun addComment(token: String, userId: Int, eventId: Int, text: String) {
        viewModelScope.launch {
            try {
                repository.addComment(token, userId, eventId, text)
                loadComments(eventId)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}