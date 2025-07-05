package com.example.eventplannerteam22.profile

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.example.eventplannerteam22.router.Screen
import com.example.eventplannerteam22.session.SessionViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun ProfileScreen(
    navController: NavController,
    coroutineScope: CoroutineScope,
    paddingValues: PaddingValues,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    sessionViewModel: SessionViewModel = hiltViewModel(LocalContext.current as ComponentActivity)

) {

    val userProfile = profileViewModel.userProfile
    val session = sessionViewModel.session.collectAsState()

    LaunchedEffect(Unit) {
        if (session.value.loggedIn) {
            profileViewModel.loadUserProfile(session.value.accessToken)
        }
    }

    if (userProfile == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Profile", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            ProfileItem("Name", userProfile.name.trim())
            ProfileItem("Surname", userProfile.surname)
            ProfileItem("Email", userProfile.email)
            ProfileItem("Phone", userProfile.phone ?: "Not provided")
            ProfileItem("Address", userProfile.homeAddress ?: "Not provided")
            ProfileItem("Role", userProfile.role)
            ProfileItem("Username", userProfile.username)
            ProfileItem("Enabled", userProfile.enabled.toString())
            ProfileItem(
                "Authorities",
                userProfile.authorities.joinToString { it.toString() }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                navController.navigate(Screen.MainScreen.route)
                sessionViewModel.clearSession()
                profileViewModel.unloadUserProfile()
            }) {
                Text("Logout")
            }
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
