package com.example.eventplannerteam22.admin.comments.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.admin.comments.data.repository.AdminCommentRepository
import com.example.eventplannerteam22.admin.comments.domain.AdminComment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminCommentModerationViewModel @Inject constructor(
    private val repository: AdminCommentRepository
) : ViewModel() {

    private val _comments = MutableStateFlow<List<AdminComment>>(emptyList())
    val comments: StateFlow<List<AdminComment>> = _comments

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var selectedType = "product"

    fun loadComments(type: String = selectedType) {
        selectedType = type
        _comments.value = emptyList()
        viewModelScope.launch {
            try {
                _comments.value = repository.getUnapproved(type)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun approve(id: Int) {
        viewModelScope.launch {
            try {
                repository.approveComment(selectedType, id)
                loadComments()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}