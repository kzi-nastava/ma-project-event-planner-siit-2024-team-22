package com.example.eventplannerteam22.booking.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.booking.data.repository.BookingRepository
import com.example.eventplannerteam22.booking.domain.model.Booking
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.solutions.data.SolutionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupplierBookingViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val solutionRepository: SolutionRepository
) : ViewModel() {
    var bookings by mutableStateOf<List<Booking>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    fun loadSupplierBookings(supplierId: Int) {
        isLoading = true
        viewModelScope.launch {
            try {
                val allSolutions = solutionRepository.getSolutions(100, 0)
                
                val supplierSolutions = allSolutions.filter { 
                    it.createdBy?.id == supplierId 
                }
                
                if (supplierSolutions.isEmpty()) {
                    bookings = emptyList()
                    error = null
                    isLoading = false
                    return@launch
                }
                
                val allBookings = mutableListOf<Booking>()
                for (solution in supplierSolutions) {
                    when (val result = bookingRepository.getSolutionBookings(solution.id)) {
                        is ApiResult.Success -> {
                            allBookings.addAll(result.data)
                        }
                        is ApiResult.UnknownError -> {
                            error = result.message
                        }
                        else -> {}
                    }
                }
                
                bookings = allBookings
                error = null
            } catch (e: Exception) {
                error = e.message ?: "Failed to load bookings"
            } finally {
                isLoading = false
            }
        }
    }

    fun confirmBooking(bookingId: Int) {
        isLoading = true
        viewModelScope.launch {
            when (val result = bookingRepository.confirmBooking(bookingId)) {
                is ApiResult.Success -> {
                    bookings = bookings.map { booking ->
                        if (booking.id == bookingId) {
                            booking.copy(confirmed = true)
                        } else {
                            booking
                        }
                    }
                    error = null
                }
                is ApiResult.UnknownError -> {
                    error = result.message
                }
                else -> {
                    error = "Failed to confirm booking"
                }
            }
            isLoading = false
        }
    }

    fun clearError() {
        error = null
    }
}
