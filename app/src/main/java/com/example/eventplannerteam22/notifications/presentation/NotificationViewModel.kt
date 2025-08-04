package com.example.eventplannerteam22.notifications.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.eventplannerteam22.notifications.data.model.NotificationDto
import com.example.eventplannerteam22.notifications.data.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel() {
    private val _notifications = mutableStateOf<List<NotificationDto>>(emptyList())
    val notifications: State<List<NotificationDto>> = _notifications

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun loadNotifications(userId: Int) {
        viewModelScope.launch {
            try {
                _notifications.value = repository.getNotifications(userId)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}