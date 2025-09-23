package com.example.eventplannerteam22.booking.data.repository

import com.example.eventplannerteam22.booking.data.remote.BookingApi
import com.example.eventplannerteam22.booking.data.remote.CreateBookingRequest
import com.example.eventplannerteam22.booking.domain.model.Booking
import com.example.eventplannerteam22.network.ApiResult
import javax.inject.Inject

class BookingRepository @Inject constructor(
    private val api: BookingApi
) {
    suspend fun getUserBookings(userId: Int): ApiResult<List<Booking>> {
        return try {
            val bookings = api.getUserBookings(userId)
            ApiResult.Success(bookings)
        } catch (e: Exception) {
            ApiResult.UnknownError(-1, e.message)
        }
    }

    suspend fun createBooking(request: CreateBookingRequest): ApiResult<Booking> {
        return try {
            val booking = api.createBooking(request)
            ApiResult.Success(booking)
        } catch (e: Exception) {
            ApiResult.UnknownError(-1, e.message)
        }
    }
}
