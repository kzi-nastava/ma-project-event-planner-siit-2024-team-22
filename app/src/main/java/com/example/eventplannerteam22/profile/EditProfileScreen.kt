package com.example.eventplannerteam22.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.auth.ValidatingInputTextField

@Composable
fun EditProfileScreen(
    editProfileViewModel: EditProfileViewModel = hiltViewModel(),
    navController: NavController
) {
    val screenState = editProfileViewModel.screenState
    val userProfile = navController.previousBackStackEntry
        ?.savedStateHandle?.get<Profile>("userProfile")
    LaunchedEffect(Unit) {
        editProfileViewModel.initScreenState(userProfile ?: Profile())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ValidatingInputTextField(
            label = "Name",
            value = screenState.name,
            onValueChange = { input ->
                editProfileViewModel.onEvent(
                    EditProfileUIEvent.NameChanged(input)
                )
            },
            isError = screenState.nameErrorText != null,
            errorText = screenState.nameErrorText
        )
        ValidatingInputTextField(
            label = "Surname",
            value = screenState.surname,
            onValueChange = { input ->
                editProfileViewModel.onEvent(
                    EditProfileUIEvent.SurnameChanged(input)
                )
            },
            isError = screenState.surnameErrorText != null,
            errorText = screenState.surnameErrorText
        )
//        ValidatingInputTextField(
//            label = "Phone",
//            value = screenState.phone,
//            onValueChange = { input ->
//                editProfileViewModel.onEvent(
//                    EditProfileUIEvent.PhoneChanged(
//                        input
//                    )
//                )
//            },
//            isError = screenState.phoneErrorText != null,
//            errorText = screenState.phoneErrorText,
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
//        )
        ValidatingInputTextField(
            label = "Email",
            value = screenState.email,
            onValueChange = { input ->
                editProfileViewModel.onEvent(
                    EditProfileUIEvent.EmailChanged(
                        input
                    )
                )
            },
            isError = screenState.emailErrorText != null,
            errorText = screenState.emailErrorText,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
//        ValidatingInputTextField(
//            label = "Address",
//            value = screenState.address,
//            onValueChange = { input ->
//                editProfileViewModel.onEvent(
//                    EditProfileUIEvent.AddressChanged(
//                        input
//                    )
//                )
//            },
//            isError = screenState.addressErrorText != null,
//            errorText = screenState.addressErrorText,
//        )
//        Button(
//            onClick = TODO()
//        ) {
//            Text("Submit")
//        }
    }
}