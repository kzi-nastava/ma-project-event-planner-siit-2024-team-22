package com.example.eventplannerteam22.profile.presentation.editprofile

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.auth0.jwt.JWT
import com.example.eventplannerteam22.auth.ValidatingInputTextField
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.apiResultHandler
import com.example.eventplannerteam22.profile.domen.Profile
import com.example.eventplannerteam22.router.Screen
import com.example.eventplannerteam22.session.SessionViewModel


@Composable
fun EditProfileScreen(
    editProfileViewModel: EditProfileViewModel = hiltViewModel(),
    sessionViewModel: SessionViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    navController: NavController
) {
    val screenState = editProfileViewModel.screenState
    val userProfile = navController.previousBackStackEntry
        ?.savedStateHandle?.get<Profile>("userProfile")
    val session = sessionViewModel.session.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        editProfileViewModel.initScreenState(userProfile ?: Profile())
    }

    LaunchedEffect(editProfileViewModel.apiResults) {
        editProfileViewModel.apiResults.collect { result ->
            apiResultHandler<Profile>(
                onSuccess = {
                    when (result) {
                        is ApiResult.Success -> {
                            navController.navigate(Screen.Profile.route) {
                                popUpTo(Screen.EditProfile.route) { inclusive = true }
                            }
                        }

                        else -> Unit
                    }
                },
                apiResult = result,
                logTag = "EditProfileScreen",
                context = context,
            )
        }

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
        Button(
            onClick = {
                Log.i("EditProfileScreen", session.value.accessToken)
                editProfileViewModel.onEvent(
                    EditProfileUIEvent.SubmitChanges(
                        JWT.decode(
                            session.value.accessToken
                        ).getClaim("userId").asInt()
                    )
                )
            }
        ) {
            Text("Submit")
        }
    }
}