package com.example.eventplannerteam22.presentation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.router.Screen
import com.example.eventplannerteam22.session.SessionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    sessionViewModel: SessionViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    navController: NavController,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    drawerContent: @Composable () -> Unit,
    topBarTitle: String = "Event Planner",
    content: @Composable (PaddingValues) -> Unit,
) {

    val session = sessionViewModel.session.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { ModalDrawerSheet { drawerContent() } }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    title = { Text(topBarTitle) },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        if (session.value.loggedIn) {
                            IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                                Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                            }
                        } else {
                            IconButton(onClick = { navController.navigate(Screen.Auth.route) }) {
                                Icon(
                                    Icons.Default.ExitToApp,
                                    contentDescription = "Login or register"
                                )
                            }
                        }
                    }
                )
            },
            content = content
        )
    }
}