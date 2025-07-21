package com.example.eventplannerteam22.eventType

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventplannerteam22.auth.ValidatingInputTextField

@Composable
fun CreateEventType(
    createEventTypeViewModel: CreateEventTypeViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        ValidatingInputTextField(
            label = "Description",
            value = createEventTypeViewModel.screenState.description,
            onValueChange = {},
            isError = createEventTypeViewModel.screenState.descriptionError.isBlank(),
            errorText = createEventTypeViewModel.screenState.descriptionError
        )
    }
}