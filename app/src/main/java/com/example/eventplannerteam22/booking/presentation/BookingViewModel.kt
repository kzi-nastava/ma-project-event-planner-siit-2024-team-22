package com.example.eventplannerteam22.booking.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.booking.data.remote.CreateBookingRequest
import com.example.eventplannerteam22.booking.data.repository.BookingRepository
import com.example.eventplannerteam22.booking.domain.model.Booking
import com.example.eventplannerteam22.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val repository: BookingRepository
) : ViewModel() {
    var bookings by mutableStateOf<List<Booking>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    fun loadBookings(userId: Int) {
        isLoading = true
        viewModelScope.launch {
            when (val result = repository.getUserBookings(userId)) {
                is ApiResult.Success -> {
                    bookings = result.data
                    error = null
                }
                is ApiResult.UnknownError -> {
                    error = result.message
                }
                else -> {}
            }
            isLoading = false
        }
    }

    fun createBooking(request: CreateBookingRequest, onSuccess: () -> Unit) {
        isLoading = true
        viewModelScope.launch {
            when (val result = repository.createBooking(request)) {
                is ApiResult.Success -> {
                    loadBookings(request.userId)
                    onSuccess()
                }
                is ApiResult.UnknownError -> {
                    error = result.message
                }
                else -> {}
            }
            isLoading = false
        }
    }
}
