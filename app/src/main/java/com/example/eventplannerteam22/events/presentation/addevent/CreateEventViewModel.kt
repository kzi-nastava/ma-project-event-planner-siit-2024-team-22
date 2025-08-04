package com.example.eventplannerteam22.events.presentation.addevent

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.events.data.model.CreateEventDTO
import com.example.eventplannerteam22.events.data.repository.EventRepository
import com.example.eventplannerteam22.eventtype.data.repository.EventTypeRepository
import com.example.eventplannerteam22.eventtype.domen.EventTypeListItem
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.session.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val eventTypeRepository: EventTypeRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {
    var screenState by mutableStateOf(CreateEventState())
        private set

    private val submitResultsChannel = Channel<ApiResult<Unit>>()
    val submitResults = submitResultsChannel.receiveAsFlow()

    private val fetchResultChannel = Channel<ApiResult<List<EventTypeListItem>>>()
    val fetchResults = fetchResultChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            fetchResultChannel.send(eventTypeRepository.getAllEventTypes())
        }
    }

    fun onEvent(event: CreateEventUiEvent) {
        when (event) {
            is CreateEventUiEvent.NameChanged -> {
                screenState = screenState.copy(
                    name = event.input,
                    nameError = isValidName(event.input)
                )
            }

            is CreateEventUiEvent.DescriptionChanged -> {
                screenState = screenState.copy(
                    description = event.input,
                    descriptionError = isValidDescription(event.input)
                )
            }

            is CreateEventUiEvent.EventTypeIdChanged -> {
                screenState = screenState.copy(eventTypeId = event.input)
            }

            is CreateEventUiEvent.MaxCapacityChanged -> {
                if (event.input.isDigitsOnly())
                    screenState = screenState.copy(
                        maxCapacity = event.input,
                        maxCapacityError = isValidMaxCapacity(event.input)
                    )
            }

            is CreateEventUiEvent.IsPrivateChanged -> {
                screenState = screenState.copy(isPrivate = event.input)
            }

            is CreateEventUiEvent.LocationChanged -> {
                screenState = screenState.copy(
                    location = event.input,
                    locationError = isValidLocation(event.input)
                )
            }

            is CreateEventUiEvent.DateOfEventChanged -> {
                screenState = screenState.copy(dateOfEvent = event.input)
            }

            is CreateEventUiEvent.AddActivity -> {
                screenState = screenState.copy(
                    eventActivities = screenState.eventActivities + event.activity
                )
            }

            is CreateEventUiEvent.RemoveActivity -> {
                screenState = screenState.copy(
                    eventActivities = screenState.eventActivities.toMutableList().also {
                        it.removeAt(event.index)
                    }
                )
            }

            is CreateEventUiEvent.Submit -> {
                Log.d("CreateEventViewModel", screenState.eventActivities.toString())
                submit()
            }
        }
    }

    fun loadEventTypes(result: ApiResult<List<EventTypeListItem>>) {
        when (result) {
            is ApiResult.Success -> screenState = screenState.copy(availableEventTypes = result.data)
            else -> Unit
        }
    }

    fun isAllValid(): Boolean {
        val nameError = isValidName(screenState.name)
        val descriptionError = isValidDescription(screenState.description)
        val locationError = isValidLocation(screenState.location)
        val maxCapacityError = isValidMaxCapacity(screenState.maxCapacity)
        val eventTypeError = isEventTypeValid()

        screenState = screenState.copy(
            nameError = nameError,
            descriptionError = descriptionError,
            locationError = locationError,
            maxCapacityError = maxCapacityError,
            eventTypeError = eventTypeError
        )

        return listOf(nameError, descriptionError, locationError, maxCapacityError).all { it == null }
    }

    fun isValidName(name: String): String? {
        return when {
            name.isBlank() -> "Name cannot be blank"
            name.length < 3 || name.length > 50 -> "Name must be between 2 and 50 characters long"
            else -> null
        }
    }

    fun isValidDescription(description: String): String? {
        return when {
            description.isBlank() -> "Description cannot be blank"
            description.length < 3 || description.length > 100 -> "Description must be between 2 and 50 characters long"
            else -> null
        }
    }

    fun isEventTypeValid(): String? {
        return when {
            screenState.eventTypeId == -1 -> "Select an event type"
            else -> null
        }
    }

    fun isValidMaxCapacity(capacity: String): String? {
        return when {
            capacity.isBlank() -> "Capacity cannot be blank"
            capacity.toInt() < 1 -> "Capacity cannot be less than 1"
            else -> null
        }
    }

    fun isValidLocation(location: String): String? {
        return when {
            location.isBlank() -> "Location cannot be blank"
            location.length < 3 || location.length > 100 -> "Location must be between 3 and 100 characters long"
            else -> null
        }
    }

    fun submit() {
        viewModelScope.launch {
            eventRepository.addEvent(
                CreateEventDTO(
                    name = screenState.name,
                    description = screenState.description,
                    eventTypeId = screenState.eventTypeId,
                    maxCapacity = screenState.maxCapacity.toInt(),
                    isPrivate = screenState.isPrivate,
                    location = screenState.location,
                    dateOfEvent = screenState.dateOfEvent,
                    userId = sessionRepository.getUserId(),
                    eventActivities = screenState.eventActivities
                )
            )
        }
    }
}
