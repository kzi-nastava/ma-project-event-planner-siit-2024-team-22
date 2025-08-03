package com.example.eventplannerteam22.solutions

import androidx.lifecycle.ViewModel
import com.example.eventplannerteam22.solutionCategory.domain.SolutionCategory
import com.example.eventplannerteam22.solutions.data.SolutionRepository
import com.example.eventplannerteam22.solutions.domain.CreateSolutionDTO
import com.example.eventplannerteam22.solutions.domain.ServiceBookingConfirmType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddSolutionViewModel @Inject constructor(
    private val repository: SolutionRepository
) : ViewModel() {

    private val _addSolutionResult = MutableSharedFlow<AddSolutionResult>()
    val addSolutionResult = _addSolutionResult.asSharedFlow()

    suspend fun addSolution(
        name: String,
        description: String,
        features: String,
        price: String,
        discount: String,
        durationHours: String,
        durationMinutes: String,
        dateStartBooking: String,
        dateFinishBooking: String,
        createdBy: Int
    ) {
        try {
            repository.addSolution(
                CreateSolutionDTO(
                    name = name,
                    category = SolutionCategory(1, null, null),
                    description = description,
                    features = features,
                    price = price.toDouble(),
                    discount = discount.toDouble(),
                    imgUrl = "",
                    visible = true,
                    duration = parseDuration(durationHours, durationMinutes),
                    dateStartBooking = LocalDate.parse(dateStartBooking),
                    dateFinishBooking = LocalDate.parse(dateFinishBooking),
                    bookingConfirmType = ServiceBookingConfirmType.Manual,
                    isDeleted = false,
                    createdBy = createdBy
                )
            )
            _addSolutionResult.emit(AddSolutionResult.Success)
        } catch (e: Exception) {
            _addSolutionResult.emit(AddSolutionResult.Failure(e.message ?: "Unknown error"))
        }
    }
}

private fun parseDuration(hoursStr: String, minutesStr: String): java.time.Duration {
    val hours = hoursStr.toLongOrNull() ?: 0L
    val minutes = minutesStr.toLongOrNull() ?: 0L
    return java.time.Duration.ofHours(hours).plusMinutes(minutes)
}

sealed class AddSolutionResult {
    object Success : AddSolutionResult()
    data class Failure(val message: String) : AddSolutionResult()
}