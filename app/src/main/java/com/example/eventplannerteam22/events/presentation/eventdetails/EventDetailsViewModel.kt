package com.example.eventplannerteam22.events.presentation.eventdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.events.data.repository.EventRepository
import com.example.eventplannerteam22.events.domen.Event
import com.example.eventplannerteam22.events.invite.data.EventInviteRepository
import com.example.eventplannerteam22.favorites.data.model.FavoriteEventRequestDTO
import com.example.eventplannerteam22.favorites.data.repository.FavoriteRepository
import com.example.eventplannerteam22.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val repository: EventRepository,
    private val inviteRepository: EventInviteRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val fetchResultChannel = Channel<ApiResult<Event>>()
    val fetchResult = fetchResultChannel.receiveAsFlow()

    private val _inviteResult = MutableStateFlow<String?>(null)
    val inviteResult: StateFlow<String?> = _inviteResult

    fun loadEvent(eventId: Int) {
        viewModelScope.launch {
            val result = repository.getEventById(eventId)
            fetchResultChannel.send(result)
            if (result is ApiResult.Success) {
                _event.value = result.data
            }
        }
    }

    fun sendInvites(token: String, eventId: Int, emails: List<String>) {
        viewModelScope.launch {
            try {
                inviteRepository.inviteUsers(token, eventId, emails)
                _inviteResult.value = "Invites sent successfully"
            } catch (e: Exception) {
                _inviteResult.value = e.message
            }
        }
    }
    fun loadFavoriteStatus(eventId: Int, userId: Int) {
        viewModelScope.launch {
            val favoriteEvents = favoriteRepository.getEventFavorites(userId)
            _isFavorite.value = favoriteEvents.any { it.id == eventId }
        }
    }

    fun addToFavorites(eventId: Int, userId: Int) {
        viewModelScope.launch {
            favoriteRepository.addEventFavorite(FavoriteEventRequestDTO(userId = userId, eventId = eventId))
            _isFavorite.value = true
        }
    }


    fun removeFromFavorites(eventId: Int, userId: Int) {
        viewModelScope.launch {
            favoriteRepository.removeEventFavorite(userId, eventId)
            _isFavorite.value = false
        }
    }
}
