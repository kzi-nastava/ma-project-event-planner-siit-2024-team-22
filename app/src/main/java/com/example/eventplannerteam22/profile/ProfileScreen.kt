package com.example.eventplannerteam22.profile

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.network.apiResultHandler
import com.example.eventplannerteam22.router.Screen
import com.example.eventplannerteam22.session.SessionViewModel
import kotlinx.coroutines.CoroutineScope

const val LOG_TAG = "ProfileScreen"

@Composable
fun ProfileScreen(
    navController: NavController,
    coroutineScope: CoroutineScope,
    paddingValues: PaddingValues,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    sessionViewModel: SessionViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val context = LocalContext.current
    val userProfile = profileViewModel.userProfile
    val session = sessionViewModel.session.collectAsState()

    LaunchedEffect(session.value.loggedIn, context) {
        if (session.value.loggedIn) {
            profileViewModel.loadUserProfile(sessionViewModel.session.value.accessToken)
        }
    }

    LaunchedEffect(Unit) {
        profileViewModel.apiResults.collect { result ->
            apiResultHandler(
                apiResult = result,
                logTag = LOG_TAG,
                context = context,
                onSuccess = {}
            )
        }
    }

    if (profileViewModel.screenState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                "My Profile",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileItem("Name", userProfile.name.trim())
                    ProfileItem("Surname", userProfile.surname)
                    ProfileItem("Email", userProfile.email)
                    ProfileItem("Phone", userProfile.phone ?: "Not provided")
                    ProfileItem("Address", userProfile.homeAddress ?: "Not provided")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "userProfile",
                            userProfile
                        )

                        navController.navigate(Screen.EditProfile.route)
                    }
                ) {
                    Text("Edit")
                }

                Spacer(modifier = Modifier.width(32.dp))

                Button(
                    onClick = {
                        navController.navigate(Screen.Main.route)
                        sessionViewModel.clearSession()
                        profileViewModel.unloadUserProfile()
                    }
                ) {
                    Text("Logout")
                }
            }
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
