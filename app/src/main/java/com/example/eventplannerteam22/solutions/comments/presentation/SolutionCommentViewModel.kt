// presentation/SolutionCommentViewModel.kt
package com.example.eventplannerteam22.solutions.comments.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.solutions.comments.data.SolutionCommentRepository
import com.example.eventplannerteam22.solutions.comments.domain.SolutionComment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolutionCommentViewModel @Inject constructor(
    private val repository: SolutionCommentRepository
) : ViewModel() {

    private val _comments = MutableStateFlow<List<SolutionComment>>(emptyList())
    val comments: StateFlow<List<SolutionComment>> = _comments

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadComments(solutionId: Int) {
        viewModelScope.launch {
            try {
                _comments.value = repository.getComments(solutionId)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun addComment(token: String, userId: Int, solutionId: Int, text: String) {
        viewModelScope.launch {
            try {
                repository.addComment(token, userId, solutionId, text)
                loadComments(solutionId)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}