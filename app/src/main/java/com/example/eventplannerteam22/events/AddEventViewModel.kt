package com.example.eventplannerteam22.events

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.solutions.Solution
import com.example.eventplannerteam22.solutions.SolutionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var selectedEventTypeId by mutableStateOf<Int?>(null)
    var maxCapacity by mutableStateOf("")
    var location by mutableStateOf("")
    var dateOfEvent by mutableStateOf("")
    var selectedSolutionId by mutableStateOf<Int?>(null)

    private val _createEventResult = MutableSharedFlow<Boolean>()
    val createEventResult = _createEventResult.asSharedFlow()

    fun createEvent() {
        if (name.isBlank() || description.isBlank() || selectedEventTypeId == null ||
            maxCapacity.isBlank() || location.isBlank() || dateOfEvent.isBlank() || selectedSolutionId == null) {
            return
        }

        viewModelScope.launch {
            val eventRequest = CreateEventRequest(
                name = name,
                description = description,
                eventType = EventTypeIdDto(selectedEventTypeId!!),
                maxCapacity = maxCapacity.toInt(),
                isPrivate = false,
                location = location,
                dateOfEvent = dateOfEvent,
                isDeleted = false,
                solution = SolutionIdDto(selectedSolutionId!!)
            )

            val success = try {
                repository.addEvent(eventRequest)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }

            _createEventResult.emit(success)
        }
    }
}