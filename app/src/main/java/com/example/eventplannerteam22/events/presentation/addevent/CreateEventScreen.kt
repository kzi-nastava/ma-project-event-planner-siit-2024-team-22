package com.example.eventplannerteam22.events.presentation.addevent

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.auth.ValidatingInputTextField
import com.example.eventplannerteam22.eventactivity.data.model.CreateEventActivityDTO
import com.example.eventplannerteam22.eventtype.domen.EventTypeListItem
import com.example.eventplannerteam22.eventtype.presentation.eventtypelist.LOG_TAG
import com.example.eventplannerteam22.network.apiResultHandler
import com.example.eventplannerteam22.router.Screen
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: CreateEventViewModel = hiltViewModel()
) {
    val state = viewModel.screenState
    val context = LocalContext.current
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.dateOfEvent
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )

    LaunchedEffect(Unit) {
        viewModel.fetchResults.collect { result ->
            apiResultHandler(
                apiResult = result,
                logTag = LOG_TAG,
                context = context,
                onSuccess = { viewModel.loadEventTypes(result) }
            )
        }
    }

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val selectedDate = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            viewModel.onEvent(CreateEventUiEvent.DateOfEventChanged(selectedDate))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
    ) {
        ValidatingInputTextField(
            value = state.name,
            label = "Name",
            onValueChange = { value -> viewModel.onEvent(CreateEventUiEvent.NameChanged(value)) },
            isError = state.nameError != null,
            errorText = state.nameError
        )

        Spacer(modifier = Modifier.height(8.dp))

        ValidatingInputTextField(
            value = state.description,
            label = "Description",
            onValueChange = { value -> viewModel.onEvent(CreateEventUiEvent.DescriptionChanged(value)) },
            isError = state.descriptionError != null,
            errorText = state.descriptionError
        )

        Spacer(modifier = Modifier.height(8.dp))

        EventTypeDropdown(
            state.eventTypeId,
            state.eventTypeError,
            state.availableEventTypes
        ) { id -> viewModel.onEvent(CreateEventUiEvent.EventTypeIdChanged(id)) }

        Spacer(modifier = Modifier.height(8.dp))

        ValidatingInputTextField(
            value = state.maxCapacity,
            label = "Max capacity",
            onValueChange = { value -> viewModel.onEvent(CreateEventUiEvent.MaxCapacityChanged(value)) },
            isError = state.maxCapacityError != null,
            errorText = state.maxCapacityError
        )

        Spacer(modifier = Modifier.height(8.dp))

        ValidatingInputTextField(
            value = state.location,
            label = "Location",
            onValueChange = { value -> viewModel.onEvent(CreateEventUiEvent.LocationChanged(value)) },
            isError = state.locationError != null,
            errorText = state.locationError
        )

        Spacer(modifier = Modifier.height(8.dp))

        DatePicker(
            state = datePickerState,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = state.isPrivate,
                onCheckedChange = { value -> viewModel.onEvent(CreateEventUiEvent.IsPrivateChanged(value)) }
            )
            Text("Private Event")
        }

        ActivityInputSection(context) { viewModel.onEvent(CreateEventUiEvent.AddActivity(it)) }

        Column {
            Text("Activities:")
            state.eventActivities.forEachIndexed { index, activity ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("${activity.name} (${activity.startTime} - ${activity.endTime}) @ ${activity.location}")
                    Button(onClick = { viewModel.onEvent(CreateEventUiEvent.RemoveActivity(index)) }) {
                        Text("Remove")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (viewModel.isAllValid()) {
                    viewModel.onEvent(CreateEventUiEvent.Submit)
                    navController.navigate(Screen.Events.route)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Event")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventTypeDropdown(
    selectedEventTypedId: Int,
    eventTypeError: String?,
    eventTypes: List<EventTypeListItem>,
    onTypeSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedText = eventTypes.find { it.id == selectedEventTypedId }?.name ?: "Select Event Type"
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        ValidatingInputTextField(
            label = "Event type",
            value = selectedText,
            onValueChange = {},
            isError = eventTypeError != null,
            errorText = eventTypeError,
            modifier = Modifier.menuAnchor(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            eventTypes.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.name) },
                    onClick = {
                        onTypeSelected(type.id)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ActivityInputSection(
    context: Context,
    onAddActivity: (CreateEventActivityDTO) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf(LocalTime.of(9, 0)) }
    var endTime by remember { mutableStateOf(LocalTime.of(10, 0)) }

    Column {
        ValidatingInputTextField(
            value = name,
            label = "Activity Name",
            onValueChange = { name = it },
            isError = false,
            errorText = ""
        )
        ValidatingInputTextField(
            value = location,
            label = "Activity Location",
            onValueChange = { location = it },
            isError = false,
            errorText = ""
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = {
                val time = startTime
                TimePickerDialog(
                    context,
                    { _, hour, minute -> startTime = LocalTime.of(hour, minute) },
                    time.hour,
                    time.minute,
                    true
                ).show()
            }) {
                Text("Start Time: ${startTime.format(DateTimeFormatter.ofPattern("HH:mm"))}")
            }

            TextButton(onClick = {
                val time = endTime
                TimePickerDialog(
                    context,
                    { _, hour, minute -> endTime = LocalTime.of(hour, minute) },
                    time.hour,
                    time.minute,
                    true
                ).show()
            }) {
                Text("End Time: ${endTime.format(DateTimeFormatter.ofPattern("HH:mm"))}")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (name.isNotBlank() && location.isNotBlank()) {
                onAddActivity(CreateEventActivityDTO(name, startTime, endTime, location))
                name = ""
                location = ""
            }
        }) {
            Text("Add Activity")
        }
    }
}
