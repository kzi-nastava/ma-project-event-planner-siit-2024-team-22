package com.example.eventplannerteam22.solutions

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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
        duration: String,
        dateStartBooking: String,
        dateFinishBooking: String
    ) {
        try {
            repository.addSolution(
                Solution(
                    id = 0,
                    name = name,
                    category = Category(1, "IT Services", "ACCEPTED"),
                    description = description,
                    features = features,
                    price = price.toDouble(),
                    discount = discount.toDouble(),
                    imgUrl = "",
                    visible = true,
                    duration = java.time.Duration.parse(duration),
                    dateStartBooking = java.time.LocalDate.parse(dateStartBooking),
                    dateFinishBooking = java.time.LocalDate.parse(dateFinishBooking),
                    bookingConfirmType = "Manual",
                    isDeleted = false
                )
            )
            _addSolutionResult.emit(AddSolutionResult.Success)
        } catch (e: Exception) {
            _addSolutionResult.emit(AddSolutionResult.Failure(e.message ?: "Unknown error"))
        }
    }
}

sealed class AddSolutionResult {
    object Success : AddSolutionResult()
    data class Failure(val message: String) : AddSolutionResult()
}