package com.example.eventplannerteam22.eventType

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.auth.ValidatingInputTextField
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.apiResultHandler
import com.example.eventplannerteam22.router.Screen

@Composable
fun CreateEventType(
    createEventTypeViewModel: CreateEventTypeViewModel = hiltViewModel(),
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues()
) {
    val context = LocalContext.current

    LaunchedEffect(createEventTypeViewModel, context) {
        createEventTypeViewModel.results.collect { result ->
            apiResultHandler(
                onSuccess = {
                    if (result is ApiResult.Success) {
                        navController.navigate(Screen.EventTypes.route) {
                            popUpTo(Screen.CreateEventType.route) { inclusive = true }
                        }
                    }
                },
                apiResult = result,
                logTag = "CreateEventTypeScreen",
                context = context
            )
        }
    }

    val state = createEventTypeViewModel.screenState

    CreateEventTypeContent(
        name = state.name,
        nameError = state.nameError,
        onNameChange = {
            createEventTypeViewModel.onEvent(
                CreateEventTypeUiEvent.NameChanged(
                    it
                )
            )
        },
        description = state.description,
        descriptionError = state.descriptionError,
        onDescriptionChange = {
            createEventTypeViewModel.onEvent(
                CreateEventTypeUiEvent.DescriptionChanged(
                    it
                )
            )
        },
        onSubmit = {
            createEventTypeViewModel.onEvent(
                CreateEventTypeUiEvent.Submit
            )
        }
    )
}

@Composable
fun CreateEventTypeContent(
    name: String,
    nameError: String?,
    onNameChange: (String) -> Unit,
    description: String,
    descriptionError: String?,
    onDescriptionChange: (String) -> Unit,
    onSubmit: () -> Unit

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ValidatingInputTextField(
            label = "Name",
            value = name,
            onValueChange = onNameChange,
            isError = nameError != null,
            errorText = nameError
        )
        Spacer(modifier = Modifier.height(16.dp))
        ValidatingInputTextField(
            label = "Description",
            value = description,
            onValueChange = onDescriptionChange,
            isError = descriptionError != null,
            errorText = descriptionError
        )
        Button(
            onClick = onSubmit,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateEventTypePreview() {
    CreateEventTypeContent(
        name = "Mock name",
        nameError = "Name error",
        onNameChange = {},
        description = "Mock description",
        descriptionError = "Description error",
        onDescriptionChange = {},
        onSubmit = {}
    )
}
