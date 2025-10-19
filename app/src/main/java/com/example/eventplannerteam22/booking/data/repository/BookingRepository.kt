package com.example.eventplannerteam22.booking.data.repository

import com.example.eventplannerteam22.booking.data.remote.BookingApi
import com.example.eventplannerteam22.booking.data.remote.CreateBookingRequest
import com.example.eventplannerteam22.booking.domain.model.Booking
import com.example.eventplannerteam22.network.ApiResult
import retrofit2.HttpException
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
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                409 -> "The booking overlaps with an existing booking for this service"
                400 -> {
                    e.response()?.errorBody()?.string() ?: "Event date is outside the service availability period"
                }
                else -> e.message() ?: "Failed to create booking"
            }
            ApiResult.UnknownError(e.code(), errorMessage)
        } catch (e: Exception) {
            ApiResult.UnknownError(-1, e.message ?: "Network error")
        }
    }

    suspend fun getSupplierBookings(supplierId: Int): ApiResult<List<Booking>> {
        return try {
            val bookings = api.getSolutionBookings(supplierId)
            ApiResult.Success(bookings)
        } catch (e: Exception) {
            ApiResult.UnknownError(-1, e.message ?: "Failed to load supplier bookings")
        }
    }

    suspend fun getSolutionBookings(solutionId: Int): ApiResult<List<Booking>> {
        return try {
            val bookings = api.getSolutionBookings(solutionId)
            ApiResult.Success(bookings)
        } catch (e: Exception) {
            ApiResult.UnknownError(-1, e.message ?: "Failed to load solution bookings")
        }
    }

    suspend fun confirmBooking(bookingId: Int): ApiResult<Booking> {
        return try {
            val response = api.confirmBooking(bookingId)
            if (response.isSuccessful) {
                val booking = response.body()
                if (booking != null) {
                    ApiResult.Success(booking)
                } else {
                    ApiResult.UnknownError(500, "Empty response body")
                }
            } else {
                ApiResult.UnknownError(response.code(), response.message())
            }
        } catch (e: Exception) {
            ApiResult.UnknownError(-1, e.message ?: "Failed to confirm booking")
        }
    }
}
