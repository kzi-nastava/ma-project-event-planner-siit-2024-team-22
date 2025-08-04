package com.example.eventplannerteam22.events.presentation.editevent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.auth.ValidatingInputTextField
import com.example.eventplannerteam22.events.data.model.UpdateEventDTO
import com.example.eventplannerteam22.network.apiResultHandler

@Composable
fun EditEventScreen(
    eventId: Int,
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: EditEventViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.screenState
    val eventToEdit = navController.previousBackStackEntry?.savedStateHandle?.get<UpdateEventDTO>("eventToEdit") ?: UpdateEventDTO()

    LaunchedEffect(Unit) {
        viewModel.loadInitialValues(eventToEdit)

        viewModel.submitResults.collect { result ->
            apiResultHandler(
                apiResult = result,
                logTag = "EditEventViewModel",
                context = context,
                onSuccess = {
                    navController.popBackStack()
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Edit event",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        HorizontalDivider()

        ValidatingInputTextField(
            label = "Event Name",
            value = state.name,
            onValueChange = { viewModel.onEvent(EditEventUiEvent.NameChanged(it)) },
            isError = state.nameError != null,
            errorText = state.nameError
        )
        ValidatingInputTextField(
            label = "Description",
            value = state.description,
            onValueChange = { viewModel.onEvent(EditEventUiEvent.DescriptionChanged(it)) },
            isError = state.descriptionError != null,
            errorText = state.descriptionError
        )
        ValidatingInputTextField(
            label = "Max capacity",
            value = state.maxCapacity,
            onValueChange = { viewModel.onEvent(EditEventUiEvent.MaxCapacityChanged(it)) },
            isError = state.maxCapacityError != null,
            errorText = state.maxCapacityError
        )
        ValidatingInputTextField(
            label = "Location",
            value = state.location,
            onValueChange = { viewModel.onEvent(EditEventUiEvent.LocationChanged(it)) },
            isError = state.locationError != null,
            errorText = state.locationError
        )
        Button(
            onClick = { viewModel.onEvent(EditEventUiEvent.Submit(eventId)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit changes")
        }
    }
}