package com.example.eventplannerteam22.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.events.domen.Event
import com.example.eventplannerteam22.favorites.data.model.*
import com.example.eventplannerteam22.favorites.data.repository.FavoriteRepository
import com.example.eventplannerteam22.products.domain.Product
import com.example.eventplannerteam22.solutions.domain.Solution
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteRepository
) : ViewModel() {

    private val _favoriteEvents = MutableStateFlow<List<Event>>(emptyList())
    val favoriteEvents: StateFlow<List<Event>> = _favoriteEvents

    private val _favoriteProducts = MutableStateFlow<List<Product>>(emptyList())
    val favoriteProducts: StateFlow<List<Product>> = _favoriteProducts

    private val _favoriteSolutions = MutableStateFlow<List<Solution>>(emptyList())
    val favoriteSolutions: StateFlow<List<Solution>> = _favoriteSolutions

    fun loadFavorites(userId: Int) {
        viewModelScope.launch {
            _favoriteEvents.value = repository.getEventFavorites(userId)
            _favoriteProducts.value = repository.getProductFavorites(userId)
            _favoriteSolutions.value = repository.getSolutionFavorites(userId)
        }
    }

    fun addEventToFavorites(dto: FavoriteEventRequestDTO) {
        viewModelScope.launch {
            repository.addEventFavorite(dto)
            loadFavorites(dto.userId)
        }
    }

    fun removeEventFromFavorites(userId: Int, eventId: Int) {
        viewModelScope.launch {
            repository.removeEventFavorite(userId, eventId)
            loadFavorites(userId)
        }
    }

    fun addProductToFavorites(dto: FavoriteProductRequestDTO) {
        viewModelScope.launch {
            repository.addProductFavorite(dto)
            loadFavorites(dto.userId)
        }
    }

    fun removeProductFromFavorites(userId: Int, productId: Int) {
        viewModelScope.launch {
            repository.removeProductFavorite(userId, productId)
            loadFavorites(userId)
        }
    }

    fun addSolutionToFavorites(dto: FavoriteSolutionRequestDTO) {
        viewModelScope.launch {
            repository.addSolutionFavorite(dto)
            loadFavorites(dto.userId)
        }
    }

    fun removeSolutionFromFavorites(userId: Int, solutionId: Int) {
        viewModelScope.launch {
            repository.removeSolutionFavorite(userId, solutionId)
            loadFavorites(userId)
        }
    }
}