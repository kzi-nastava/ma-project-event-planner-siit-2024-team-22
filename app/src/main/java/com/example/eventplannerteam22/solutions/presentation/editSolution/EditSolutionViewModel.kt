package com.example.eventplannerteam22.solutions.presentation.editSolution

import androidx.lifecycle.ViewModel
import com.example.eventplannerteam22.solutionCategory.domain.SolutionCategory
import com.example.eventplannerteam22.solutions.data.SolutionRepository
import com.example.eventplannerteam22.solutions.domain.PutSolutionDTO
import com.example.eventplannerteam22.solutions.domain.ServiceBookingConfirmType
import com.example.eventplannerteam22.solutions.domain.Solution
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.time.Duration
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EditSolutionViewModel @Inject constructor(
    private val repository: SolutionRepository
) : ViewModel() {
    private val _solution = MutableStateFlow<Solution?>(null)
    val solution: StateFlow<Solution?> = _solution.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    suspend fun loadSolution(id: Int) {
        _isLoading.value = true
        try {
            _solution.value = repository.getSolutionById(id)
        } catch (e: Exception) {
            _error.emit(e.message ?: "Failed to load solution")
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun updateSolution(
        id: Int,
        name: String,
        description: String,
        features: String,
        price: Double,
        discount: Double,
        duration: Duration,
        dateStartBooking: LocalDate,
        dateFinishBooking: LocalDate,
        visible: Boolean
    ) {
        try {
            repository.updateSolution(
                PutSolutionDTO(
                    name = name,
                    category = _solution.value?.category ?: SolutionCategory(1, null, null),
                    description = description,
                    features = features,
                    price = price,
                    discount = discount,
                    imgUrl = _solution.value?.imgUrl ?: "",
                    visible = visible,
                    duration = duration,
                    dateStartBooking = dateStartBooking,
                    dateFinishBooking = dateFinishBooking,
                    bookingConfirmType = _solution.value?.bookingConfirmType
                        ?: ServiceBookingConfirmType.Manual
                ),
                id
            )
            _error.emit("Solution updated successfully")
        } catch (e: Exception) {
            _error.emit(e.message ?: "Failed to update solution")
        }
    }
}