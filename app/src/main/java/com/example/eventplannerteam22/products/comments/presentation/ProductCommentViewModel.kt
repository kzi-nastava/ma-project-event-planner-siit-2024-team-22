package com.example.eventplannerteam22.products.comments.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.products.comments.data.ProductCommentRepository
import com.example.eventplannerteam22.products.comments.domain.ProductComment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductCommentViewModel @Inject constructor(
    private val repository: ProductCommentRepository
) : ViewModel() {

    private val _comments = MutableStateFlow<List<ProductComment>>(emptyList())
    val comments: StateFlow<List<ProductComment>> = _comments

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadComments(productId: Int) {
        viewModelScope.launch {
            try {
                _comments.value = repository.getComments(productId)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun addComment(token: String, userId: Int, productId: Int, text: String) {
        viewModelScope.launch {
            try {
                repository.addComment(token, userId, productId, text)
                loadComments(productId)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}