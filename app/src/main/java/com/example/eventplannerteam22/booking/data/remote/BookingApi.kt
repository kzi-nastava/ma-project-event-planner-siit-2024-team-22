package com.example.eventplannerteam22.booking.data.remote

import com.example.eventplannerteam22.booking.domain.model.Booking
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.Response

interface BookingApi {
    @GET("/bookings/user/{userId}")
    suspend fun getUserBookings(@Path("userId") userId: Int): List<Booking>

    @GET("/bookings/solution/{solutionId}")
    suspend fun getSolutionBookings(@Path("solutionId") solutionId: Int): List<Booking>

    @POST("/bookings")
    suspend fun createBooking(@Body booking: CreateBookingRequest): Booking

    @PATCH("/bookings/{bookingId}/confirm")
    suspend fun confirmBooking(@Path("bookingId") bookingId: Int): Response<Booking>
}

data class CreateBookingRequest(
    val userId: Int,
    val solutionId: Int,
    val eventId: Int,
    val startTime: String
)
