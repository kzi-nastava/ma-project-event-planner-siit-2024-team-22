package com.example.eventplannerteam22.eventType.presentation.createeventtype

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.eventType.data.repository.EventTypeRepository
import com.example.eventplannerteam22.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEventTypeViewModel @Inject constructor(
    val repository: EventTypeRepository
) : ViewModel() {
    var screenState by mutableStateOf(CreateEventTypeState())
        private set

    private val resultChannel = Channel<ApiResult<Unit>>()
    val results = resultChannel.receiveAsFlow()

    fun onEvent(event: CreateEventTypeUiEvent) {
        when (event) {
            is CreateEventTypeUiEvent.NameChanged -> {
                screenState = screenState.copy(
                    name = event.value,
                    nameError = isValidName(event.value)
                )
            }

            is CreateEventTypeUiEvent.DescriptionChanged -> {
                screenState = screenState.copy(
                    description = event.value,
                    descriptionError = isValidDescription(event.value)
                )
            }

            is CreateEventTypeUiEvent.Submit -> {
                if (isValidAll()) submit()
            }
        }
    }

    private fun submit() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            val result = repository.createEventType(screenState.name, screenState.description)
            resultChannel.send(result)
            screenState = screenState.copy(isLoading = false)
        }
    }

    private fun isValidName(name: String): String? {
        return when {
            name.isBlank() -> "Name cannot be empty"
            name.any { !(it.isLetter() || it.isWhitespace()) } -> "Name can only contain letters"
            else -> null
        }
    }

    private fun isValidDescription(description: String): String? {
        return when {
            description.isBlank() -> "Description cannot be empty"
            else -> null
        }
    }

    private fun isValidAll(): Boolean {
        val nameError = isValidName(screenState.name)
        val descriptionError = isValidDescription(screenState.description)
        screenState = screenState.copy(
            nameError = nameError,
            descriptionError = descriptionError
        )
        return listOf(
            nameError,
            descriptionError
        ).all { it == null }
    }
}